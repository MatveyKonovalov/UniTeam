package ru.uniteam.webplatform.data.services.tasks;

import org.springframework.stereotype.Component;
import ru.uniteam.models.tasks.TaskStatus;
import ru.uniteam.webplatform.data.entities.ProjectEntity;
import ru.uniteam.webplatform.data.entities.ProjectTaskEntity;
import ru.uniteam.webplatform.data.entities.UserEntity;
import ru.uniteam.webplatform.data.entities.dto.tasks.ProjectTaskGet;
import ru.uniteam.webplatform.data.entities.dto.tasks.ProjectTaskPost;
import ru.uniteam.webplatform.data.entities.dto.tasks.ProjectTaskInfo;

@Component
public class ProjectTaskMapper {
    public ProjectTaskEntity toProjectTaskEntityFromPost(ProjectTaskPost postTask,
                                                         UserEntity user,
                                                         ProjectEntity project) {
        if (postTask.userId() == user.getId() && postTask.projectId() == project.getProjectId()) {
            return new ProjectTaskEntity(postTask.title(),
                    postTask.description(),
                    TaskStatus.ACTIVE,
                    postTask.taskType(),
                    project, user, postTask.deadLine());
        }
        throw new IllegalArgumentException("ID mismatch");
    }

    public ProjectTaskGet toProjectTaskGetFromEntity(ProjectTaskEntity projectTaskEntity) {
        return new ProjectTaskGet(projectTaskEntity.getId(),
                projectTaskEntity.getTitle(),
                projectTaskEntity.getDescription(),
                projectTaskEntity.getTaskStatus(),
                projectTaskEntity.getTaskType(),
                projectTaskEntity.getProjectEntity().getProjectId(),
                projectTaskEntity.getUserEntity().getId(),
                projectTaskEntity.getDeadLine());
    }

    public ProjectTaskInfo toProjectTaskInfo(ProjectTaskEntity taskEntity){
        return new ProjectTaskInfo(taskEntity.getId(),
                taskEntity.getTitle(),
                taskEntity.getDescription(),
                taskEntity.getTaskStatus(),
                taskEntity.getTaskType(),
                taskEntity.getDeadLine());
    }
}
