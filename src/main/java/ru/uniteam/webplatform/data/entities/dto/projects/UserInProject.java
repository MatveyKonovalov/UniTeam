package ru.uniteam.webplatform.data.entities.dto.projects;

import ru.uniteam.models.exceptions.IncorrectProjectRoleException;
import ru.uniteam.models.exceptions.IncorrectUserEmailException;
import ru.uniteam.models.users.ProjectRole;
import ru.uniteam.usecases.CheckNotNull;

public record UserInProject(long userId, String userEmail, ProjectRole userRole) {
    public UserInProject{
        CheckNotNull.checkNotNull(userEmail, new IncorrectUserEmailException());
        CheckNotNull.checkNotNull(userRole, new IncorrectProjectRoleException());
    }
}
