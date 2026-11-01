package ru.uniteam.webplatform.data.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.uniteam.models.users.ProjectRole;
import ru.uniteam.webplatform.data.entities.ProjectEntity;
import ru.uniteam.webplatform.data.entities.UserEntity;
import ru.uniteam.webplatform.data.entities.UserProjectEntity;
import ru.uniteam.webplatform.data.repositories.ProjectRepository;
import ru.uniteam.webplatform.data.repositories.UserProjectRepository;
import ru.uniteam.webplatform.data.repositories.UserRepository;


import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommonHelper {
    private final UserProjectRepository userProjectRepository;

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public boolean isCaptain(UserEntity executor, List<UserProjectEntity> members) {
        return !members.stream()
                .filter(up -> up.getUserEntity().getId().equals(executor.getId()))
                .filter(up -> up.getProjectRole() == ProjectRole.CAPTAIN)
                .toList().isEmpty();
    }

    public boolean isCaptainOrSubCaptain(UserEntity user, List<UserProjectEntity> members) {
        return !members.stream()
                .filter(up -> up.getUserEntity().getId().equals(user.getId()))
                .filter(up ->
                        up.getProjectRole() == ProjectRole.CAPTAIN || up.getProjectRole() == ProjectRole.SUBCAPTAIN)
                .toList().isEmpty();
    }


    public ProjectEntity getProject(long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project with id=" + projectId + " not found"));
    }

    public UserEntity getUser(long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("The User with id=" + userId + " not found"));
    }

    public List<UserProjectEntity> getMembers(long projectId) {
        ProjectEntity project = getProject(projectId);

        return userProjectRepository.findAllByProjectEntityId(project);
    }

    public UserProjectEntity getUserFromMembersById(long userId, List<UserProjectEntity> members) {
        return members.stream()
                .filter(up -> up.getUserEntity().getId() == userId)
                .findFirst().orElseThrow(() ->
                        new IllegalArgumentException("The User with id=" + userId + " not found"));
    }

}
