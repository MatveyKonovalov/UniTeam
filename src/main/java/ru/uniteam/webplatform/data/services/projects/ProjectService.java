package ru.uniteam.webplatform.data.services.projects;

import ru.uniteam.models.University;
import ru.uniteam.models.exceptions.PermissionException;
import ru.uniteam.models.users.ProjectRole;
import ru.uniteam.webplatform.data.entities.ProjectEntity;
import ru.uniteam.webplatform.data.entities.UserEntity;
import ru.uniteam.webplatform.data.entities.UserProjectEntity;
import ru.uniteam.webplatform.data.entities.dto.projects.PostProject;
import ru.uniteam.webplatform.data.entities.dto.projects.ProjectAllInfo;
import ru.uniteam.webplatform.data.entities.dto.projects.ProjectContainer;
import ru.uniteam.webplatform.data.entities.dto.projects.ProjectInfo;
import ru.uniteam.webplatform.data.entities.dto.security.ApiResponse;
import ru.uniteam.webplatform.data.entities.dto.users.GetUserContainer;
import ru.uniteam.webplatform.data.repositories.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.uniteam.webplatform.data.repositories.UserProjectRepository;
import ru.uniteam.webplatform.data.services.users.UserMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper mapper;
    private final UserProjectRepository userProjectRepository;
    private final UserMapper userMapper;

    public ProjectAllInfo addNewProject(UserEntity captain, PostProject project) {
        if (captain.canBorrowNewProject()) {
            ProjectEntity projectEntity = mapper.toProjectEntityFromPostProject(project, captain.getUniversity());

            captain.addProjectEntity(projectEntity, ProjectRole.CAPTAIN);
            projectRepository.save(projectEntity);

            return new ProjectAllInfo(mapper.toGetProjectFromProjectEntity(projectEntity),
                    captain.getEmail(),
                    Collections.emptyList());
        }
        throw new IllegalArgumentException("Too many projects");
    }

    public ProjectContainer getProjects(University university, Integer minCourse) {
        List<ProjectEntity> projects;
        if (university == null && minCourse == null) {
            projects = projectRepository.findAll();
        } else if (university == null) {
            projects = projectRepository.findProjectEntitiesByMinCourse(minCourse);
        } else if (minCourse == null) {
            projects = projectRepository.findProjectEntitiesByUniversity(university);
        } else {
            projects = projectRepository.findProjectsByUniversityAndMinCourse(university, minCourse);
        }

        return new ProjectContainer(projects.stream().map(mapper::toGetProjectFromProjectEntity).toList());
    }

    public ApiResponse joinToProject(UserEntity user, long projectId) {
        ProjectEntity project = getProject(projectId);

        if (user.canBorrowNewProject()) {
            user.addProjectEntity(project, ProjectRole.CANDIDATE);
            return new ApiResponse("You application has been submitted");
        }
        throw new IllegalArgumentException("Too many projects");
    }

    public ProjectInfo getProjectById(long id) {
        return mapper.toGetProjectFromProjectEntity(getProject(id));
    }

    public GetUserContainer getCandidates(long projectId, UserEntity executor) {
        List<UserProjectEntity> members = getMembers(projectId);
        if (isCaptainOrSubCaptain(executor, members)) {
            return new GetUserContainer(members.stream()
                    .filter(up -> up.getProjectRole() == ProjectRole.CANDIDATE)
                    .map(UserProjectEntity::getUserEntity)
                    .map(userMapper::toGetUserFromUserEntity)
                    .toList());
        } else {
            throw new PermissionException();
        }
    }

    private ProjectEntity getProject(long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project with id=" + projectId + " not found"));
    }

    private List<UserProjectEntity> getMembers(long projectId) {
        ProjectEntity project = getProject(projectId);

        return userProjectRepository.findAllByProjectEntityId(project);
    }


    public void addNewCandidate(long projectId, long candidateId, UserEntity executor) {
        List<UserProjectEntity> members = getMembers(projectId);

        if (isCaptainOrSubCaptain(executor, members)) {
            Optional<UserProjectEntity> userInProject = members.stream()
                    .filter(up -> up.getUserEntity().getId().equals(candidateId))
                    .filter(up -> up.getProjectRole() == ProjectRole.CANDIDATE)
                    .findFirst();
            if (userInProject.isPresent()) {
                userInProject.get().setProjectRole(ProjectRole.EMPLOYEE);
            } else {
                throw new IllegalArgumentException("User with id=" + candidateId + " not found");
            }
        }
        throw new PermissionException();
    }

    public void cancelProject(long projectId, long candidateId, UserEntity executor) {
        List<UserProjectEntity> members = getMembers(projectId);

        if (isCaptainOrSubCaptain(executor, members)) {
            Optional<UserProjectEntity> userInProject = members.stream()
                    .filter(up -> up.getUserEntity().getId().equals(candidateId))
                    .filter(up -> up.getProjectRole() == ProjectRole.CANDIDATE)
                    .findFirst();

            if (userInProject.isPresent()) {
                userInProject.get().getUserEntity().projectCancel(userInProject.get());
            } else {
                throw new IllegalArgumentException("User with id=" + candidateId + " not found");
            }
        }
        throw new PermissionException();
    }

    public GetUserContainer getCancelledCandidates(long projectId, UserEntity executor) {
        List<UserProjectEntity> members = getMembers(projectId);

        if (isCaptainOrSubCaptain(executor, members)) {
            return new GetUserContainer(members.stream()
                    .filter(up -> up.getProjectRole() == ProjectRole.CANCELLED)
                    .map(up -> userMapper.toGetUserFromUserEntity(up.getUserEntity()))
                    .toList()
            );
        }
        throw new PermissionException();
    }

    public void appointAsDeputy(long projectId, long candidateId, UserEntity executor) {
        List<UserProjectEntity> members = getMembers(projectId);

        if (isCaptain(executor, members)) {
            Optional<UserProjectEntity> userInProject = members.stream()
                    .filter(up -> up.getUserEntity().getId().equals(candidateId))
                    .findFirst();

            if (userInProject.isPresent()) {
                userInProject.get().setProjectRole(ProjectRole.SUBCAPTAIN);
            } else {
                throw new IllegalArgumentException("User with id=" + candidateId + " not found");
            }
        }
        throw new PermissionException();
    }

    public void demotePosition(long projectId, long candidateId, UserEntity executor) {
        List<UserProjectEntity> members = getMembers(projectId);

        if (isCaptain(executor, members)) {
            Optional<UserProjectEntity> userInProject = members.stream()
                    .filter(up -> up.getUserEntity().getId().equals(candidateId))
                    .findFirst();

            if (userInProject.isPresent()) {
                userInProject.get().setProjectRole(ProjectRole.EMPLOYEE);
            } else {
                throw new IllegalArgumentException("User with id=" + candidateId + " not found");
            }
        }
        throw new PermissionException();

    }


    private boolean isCaptainOrSubCaptain(UserEntity user, List<UserProjectEntity> members) {
        return !members.stream()
                .filter(up -> up.getUserEntity().getId().equals(user.getId()))
                .filter(up ->
                        up.getProjectRole() == ProjectRole.CAPTAIN || up.getProjectRole() == ProjectRole.SUBCAPTAIN)
                .toList().isEmpty();
    }

    private boolean isCaptain(UserEntity executor, List<UserProjectEntity> members) {
        return !members.stream()
                .filter(up -> up.getUserEntity().getId().equals(executor.getId()))
                .filter(up -> up.getProjectRole() == ProjectRole.CAPTAIN)
                .toList().isEmpty();
    }

}
