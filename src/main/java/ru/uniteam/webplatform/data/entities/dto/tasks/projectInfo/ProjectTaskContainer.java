package ru.uniteam.webplatform.data.entities.dto.tasks.projectInfo;

import ru.uniteam.webplatform.data.entities.dto.tasks.ProjectTaskInfo;

public record ProjectTaskContainer(
        ProjectTaskInfo taskInfo,
        UserTaskInfo employeeInfo
) {
}
