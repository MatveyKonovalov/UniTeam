package ru.uniteam.webplatform.data.entities.dto.projects;

import ru.uniteam.models.users.ProjectRole;

public record UserInProject(long userId, String userEmail, ProjectRole userRole) {
}
