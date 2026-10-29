package ru.uniteam.webplatform.data.services.users;

import lombok.RequiredArgsConstructor;
import ru.uniteam.models.Skill;
import ru.uniteam.models.University;
import ru.uniteam.models.users.ProjectRole;
import ru.uniteam.webplatform.data.entities.ProjectEntity;
import ru.uniteam.webplatform.data.entities.UserEntity;
import ru.uniteam.webplatform.data.entities.UserProjectEntity;
import ru.uniteam.webplatform.data.entities.dto.projects.ProjectContainer;
import ru.uniteam.webplatform.data.entities.dto.users.GetUser;
import ru.uniteam.webplatform.data.entities.dto.users.GetUserContainer;
import ru.uniteam.webplatform.data.entities.usecases.DeleteUserInProject;
import ru.uniteam.webplatform.data.repositories.ProjectRepository;
import ru.uniteam.webplatform.data.repositories.ProjectTaskRepository;
import ru.uniteam.webplatform.data.repositories.UserProjectRepository;
import ru.uniteam.webplatform.data.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.uniteam.webplatform.data.services.CommonHelper;
import ru.uniteam.webplatform.data.services.projects.ProjectMapper;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final UserProjectRepository userProjectRepository;
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final CommonHelper commonHelper;
    private final DeleteUserInProject deleteUserInProject;


    public GetUserContainer getUsers(University university, Integer course) {
        if (university != null && course != null) {
            return new GetUserContainer
                    (mapper.toGetUserFromUserEntity(repository.findByUniversityAndCourse(university, course)));
        } else if (university != null) {
            return new GetUserContainer(mapper.toGetUserFromUserEntity(repository.findByUniversity(university)));
        } else if (course != null) {
            return new GetUserContainer(mapper.toGetUserFromUserEntity(repository.findByCourse(course)));
        } else {
            return new GetUserContainer(mapper.toGetUserFromUserEntity(repository.findAll()));
        }
    }

    public GetUser findUserById(long id) {
        return mapper.toGetUserFromUserEntity(commonHelper.getUser(id));
    }

    public void deleteUserById(long id) {
        repository.deleteById(id);
    }

    public UserEntity getUserByEmail(String email) {
        return repository.findUserEntitiesByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("The User with email=" + email + " not found"));
    }

    public GetUser addSkill(UserEntity user, Skill skill) {
        if (user.canBorrowSkill()) {
            user.addSkill(skill);

            return mapper.toGetUserFromUserEntity(user);
        }

        throw new IllegalArgumentException("Too many skills");
    }

    public GetUser deleteSkill(UserEntity userEntity, Skill skill) {
        userEntity.removeSkill(skill);

        return mapper.toGetUserFromUserEntity(userEntity);
    }

    public GetUser setUniversity(UserEntity userEntity, University university) {
        userEntity.setUniversity(university);
        return mapper.toGetUserFromUserEntity(userEntity);
    }

    public GetUser setCourse(UserEntity userEntity, int course) {
        userEntity.setCourse(course);
        return mapper.toGetUserFromUserEntity(userEntity);
    }

    /*
    Действие: Пользователь покидает проект
    Бизнес логика:
        Если текущего пользователя нет в проекте -> ничего не происходит
        Если в проекте только один пользователь -> проект удаляется
        Если пользователь зам/обычный просто удаляем
        Если в проекте есть заместитель -> он становится новым капитаном
        иначе новым капитаном становится пользователь, который присоединился раньше всех
     */
    public void leaveTheProject(UserEntity user, long projectId) {
        ProjectEntity project = commonHelper.getProject(projectId);

        List<UserProjectEntity> usersInThisProject = userProjectRepository
                .findAllByProjectEntityId(project);


        Optional<UserProjectEntity> userInThisProject = usersInThisProject.stream()
                .filter((employee) -> user.getId().equals(employee.getUserEntity().getId())).findFirst();

        if (userInThisProject.isEmpty()) return; // Если у пользователя не было этого проекта


        // Удаляем проект если в нём больше нет участников
        if (usersInThisProject.size() == 1) {
            // Удаляем пользователя с задачами
            deleteUserInProject.deleteUserFromProject(userInThisProject.get());
            // Удаляем проект
            projectRepository.delete(project);
        } else {
            // Удаляем пользователя из проекта
            deleteUserInProject.deleteUserFromProject(userInThisProject.get());
            // Если пользователь не капитан спокойно выходим
            if (userInThisProject.get().getProjectRole() != ProjectRole.CAPTAIN) return;

            // Если удаляем капитана
            Optional<UserProjectEntity> subCaptain = usersInThisProject.stream()
                    .filter((up) -> up.getProjectRole() == ProjectRole.SUBCAPTAIN)
                    .findFirst();

            if (subCaptain.isPresent()) {
                subCaptain.get().setProjectRole(ProjectRole.CAPTAIN);
            } else {
                usersInThisProject.stream()
                        .filter(up -> !up.getUserEntity().getId().equals(user.getId()))
                        .findFirst()
                        .ifPresent(up -> up.setProjectRole(ProjectRole.CAPTAIN));
            }
        }
    }

    public void giveStarProject(UserEntity executor, long projectId) {
        ProjectEntity project = commonHelper.getProject(projectId);
        executor.addProjectStar(project);
    }

    public void deleteStarProject(UserEntity executor, long projectId) {
        ProjectEntity project = commonHelper.getProject(projectId);
        executor.deleteProjectStar(project);
    }

    public ProjectContainer getProjectsStar(UserEntity executor) {
        return new ProjectContainer(executor.getProjectsStars().stream()
                .map(projectMapper::toGetProjectFromProjectEntity)
                .toList());
    }


}
