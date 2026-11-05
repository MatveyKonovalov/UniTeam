package ru.uniteam.webplatform.data.entities.usecases;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.uniteam.models.users.ProjectRole;
import ru.uniteam.webplatform.data.entities.ProjectEntity;
import ru.uniteam.webplatform.data.entities.UserEntity;
import ru.uniteam.webplatform.data.entities.UserProjectEntity;
import ru.uniteam.webplatform.data.repositories.ProjectTaskRepository;
import ru.uniteam.webplatform.data.repositories.UserProjectRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static ru.uniteam.webplatform.TestDataFactory.*;

@ExtendWith(MockitoExtension.class)
class DeleteUserInProjectTest {

    @Mock ProjectTaskRepository projectTaskRepository;
    @Mock UserProjectRepository userProjectRepository;
    @InjectMocks DeleteUserInProject deleteUserInProject;

    @Test
    void deleteUserFromProject_deletesTasksAndMembershipAndDecrementsCount() {
        UserEntity user = user(1L);
        // give user one project so currentAmountProject == 1
        ProjectEntity p = project(10L);
        user.addProjectEntity(p, ProjectRole.EMPLOYEE);
        assertEquals(1, user.getCurrentAmountProject());

        UserProjectEntity up = userProject(user, p, ProjectRole.EMPLOYEE);

        deleteUserInProject.deleteUserFromProject(up);

        verify(projectTaskRepository).deleteAllTasksByUserAndProject(user, p);
        verify(userProjectRepository).delete(up);
        assertEquals(0, user.getCurrentAmountProject());
    }
}
