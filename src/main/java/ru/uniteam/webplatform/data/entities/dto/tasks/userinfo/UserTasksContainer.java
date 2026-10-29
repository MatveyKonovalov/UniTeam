package ru.uniteam.webplatform.data.entities.dto.tasks.userinfo;

import ru.uniteam.usecases.CheckNotNull;

import java.util.List;

public record UserTasksContainer(
        List<UserTask> tasks
) {
    public UserTasksContainer{
        CheckNotNull.checkNotNull(tasks, new IllegalArgumentException("Tasks must be not null"));
    }
}
