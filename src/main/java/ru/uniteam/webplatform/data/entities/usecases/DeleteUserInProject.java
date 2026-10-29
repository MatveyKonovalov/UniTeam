package ru.uniteam.webplatform.data.entities.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.uniteam.webplatform.data.entities.UserProjectEntity;
import ru.uniteam.webplatform.data.repositories.ProjectTaskRepository;

/*
Удаление пользователя из проекта с его задачами
 */
@Component
@RequiredArgsConstructor
public class DeleteUserInProject {

    private final ProjectTaskRepository projectTaskRepository;

    public void deleteUserFromProject(UserProjectEntity userProjectEntity) {
        projectTaskRepository.deleteAllTasksByUserAndProject(userProjectEntity.getUserEntity(),
                userProjectEntity.getProjectEntity());
        userProjectEntity.getUserEntity().leaveTheProject(userProjectEntity);
    }
}
