package ru.uniteam.webplatform.data.entities.dto.tasks.projectInfo;

import java.util.List;

public record ProjectTasksContainer(
        List<ProjectTaskContainer> tasks,
        ru.uniteam.webplatform.data.entities.dto.projects.ProjectInfo projectAllInfo
) {
}
