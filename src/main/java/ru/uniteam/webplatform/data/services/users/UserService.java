package ru.uniteam.webplatform.data.services.users;

import ru.uniteam.models.Skill;
import ru.uniteam.models.University;
import ru.uniteam.models.users.ProjectRole;
import ru.uniteam.webplatform.data.entities.ProjectEntity;
import ru.uniteam.webplatform.data.entities.UserEntity;
import ru.uniteam.webplatform.data.entities.UserProjectEntity;
import ru.uniteam.webplatform.data.entities.dto.users.GetUser;
import ru.uniteam.webplatform.data.entities.dto.users.GetUserContainer;
import ru.uniteam.webplatform.data.repositories.ProjectRepository;
import ru.uniteam.webplatform.data.repositories.UserProjectRepository;
import ru.uniteam.webplatform.data.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final UserProjectRepository userProjectRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper mapper,
                       UserProjectRepository userProjectRepository, ProjectRepository projectRepository) {
        this.repository = userRepository;
        this.mapper = mapper;
        this.userProjectRepository = userProjectRepository;
        this.projectRepository = projectRepository;
    }

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
        Optional<UserEntity> user = repository.findById(id);

        if (user.isEmpty()) {
            throw new IllegalArgumentException("User with id=" + id + " not found");
        }
        return mapper.toGetUserFromUserEntity(user.get());
    }

    public void deleteUserById(long id) {
        repository.deleteById(id);
    }

    public UserEntity getUserByEmail(String email) {
        return repository.findUserEntitiesByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User with email=" + email + " not found"));
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
        ProjectEntity project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project with id=" + projectId + " not found"));

        List<UserProjectEntity> usersInThisProject = userProjectRepository
                .findAllByProjectEntityId(project);


        Optional<UserProjectEntity> userInThisProject = usersInThisProject.stream()
                .filter((employee) -> user.getId().equals(employee.getUserEntity().getId())).findFirst();

        if (userInThisProject.isEmpty()) return;

        if (usersInThisProject.size() == 1) {
            userProjectRepository.delete(userInThisProject.get());
            projectRepository.delete(project); // Удаляем проект если в нём больше нет участников
        } else {
            userProjectRepository.delete(userInThisProject.get());

            // Если пользователь не капитан
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
}
