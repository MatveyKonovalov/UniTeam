package ru.uniteam.webplatform.data.entities.usecases;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.uniteam.webplatform.data.entities.UserProjectEntity;
import ru.uniteam.webplatform.data.repositories.ProjectTaskRepository;
import ru.uniteam.webplatform.data.repositories.UserProjectRepository;

/*
Удаление пользователя из проекта с его задачами
 */
@Component
@RequiredArgsConstructor
@Transactional
public class DeleteUserInProject {

    private final ProjectTaskRepository projectTaskRepository;
    private final UserProjectRepository userProjectRepository;

    public void deleteUserFromProject(UserProjectEntity userProjectEntity) {
        projectTaskRepository.deleteAllTasksByUserAndProject(userProjectEntity.getUserEntity(),
                userProjectEntity.getProjectEntity());
        userProjectRepository.delete(userProjectEntity);
        userProjectEntity.getUserEntity().leaveTheProject();
    }
}
