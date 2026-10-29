package ru.uniteam.webplatform.data.entities.dto.tasks.userinfo;

import ru.uniteam.usecases.CheckNotNull;
import ru.uniteam.webplatform.data.entities.dto.tasks.ProjectTaskInfo;

public record UserTask(
        PrTaskInfo project,
        ProjectTaskInfo task
) {
    public UserTask{
        CheckNotNull.checkNotNull(project, new IllegalArgumentException("The project must be not null"));
        CheckNotNull.checkNotNull(task, new IllegalArgumentException("The task must be not null"));
    }
}
