package ru.uniteam.webplatform.data.entities.dto.tasks.projectInfo;

import ru.uniteam.models.users.ProjectRole;

public record UserTaskInfo(
        String firstName,
        String lastName,
        String email,
        ProjectRole projectRole
) {
}
