package ru.uniteam.webplatform.data.services.projects;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.uniteam.models.University;
import ru.uniteam.models.projects.ProjectType;
import ru.uniteam.models.users.ProjectRole;
import ru.uniteam.webplatform.data.entities.ProjectEntity;
import ru.uniteam.webplatform.data.entities.UserEntity;
import ru.uniteam.webplatform.data.entities.dto.projects.*;
import ru.uniteam.webplatform.data.entities.dto.security.ApiResponse;
import ru.uniteam.webplatform.data.entities.usecases.DeleteUserInProject;
import ru.uniteam.webplatform.data.repositories.ProjectRepository;
import ru.uniteam.webplatform.data.repositories.UserProjectRepository;
import ru.uniteam.webplatform.data.services.CommonHelper;
import ru.uniteam.webplatform.data.services.users.UserMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static ru.uniteam.webplatform.TestDataFactory.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock ProjectRepository projectRepository;
    @Mock ProjectMapper mapper;
    @Mock UserProjectRepository userProjectRepository;
    @Mock UserMapper userMapper;
    @Mock CommonHelper commonHelper;
    @Mock DeleteUserInProject deleteUserInProject;

    @InjectMocks ProjectService projectService;

    @Test
    void addNewProject_ok() {
        UserEntity captain = user(1L);
        PostProject post = new PostProject("Title", "Desc", ProjectType.DEVELOPMENT, List.of(), 1);
        ProjectEntity entity = project(10L);
        ProjectInfo info = new ProjectInfo(10L, "Title", "Desc", University.MSU, 1, 0, ProjectType.DEVELOPMENT, List.of());

        when(mapper.toProjectEntityFromPostProject(post, University.MSU)).thenReturn(entity);
        when(mapper.toGetProjectFromProjectEntity(entity)).thenReturn(info);
        when(projectRepository.save(entity)).thenReturn(entity);

        ProjectAllInfo result = projectService.addNewProject(captain, post);
        assertNotNull(result);
        verify(projectRepository).save(entity);
        assertEquals(1, captain.getCurrentAmountProject());
    }

    @Test
    void addNewProject_tooMany_throws() {
        UserEntity captain = user(1L);
        for (int i = 0; i < 5; i++) {
            captain.addProjectEntity(project(100L + i), ProjectRole.CAPTAIN);
        }
        PostProject post = new PostProject("Title", "Desc", ProjectType.DEVELOPMENT, List.of(), 1);
        assertThrows(IllegalArgumentException.class, () -> projectService.addNewProject(captain, post));
    }

    @Test
    void getProjects_all() {
        ProjectEntity p = project(1L);
        when(projectRepository.findAll()).thenReturn(List.of(p));
        when(mapper.toGetProjectFromProjectEntity(p))
                .thenReturn(new ProjectInfo(1L, "T", "D", University.MSU, 1, 0, ProjectType.DEVELOPMENT, List.of()));

        ProjectContainer container = projectService.getProjects(null, null);
        assertEquals(1, container.projects().size());
    }

    @Test
    void getProjectById() {
        ProjectEntity p = project(5L);
        when(commonHelper.getProject(5L)).thenReturn(p);
        when(mapper.toGetProjectFromProjectEntity(p))
                .thenReturn(new ProjectInfo(5L, "T", "D", University.MSU, 1, 0, ProjectType.DEVELOPMENT, List.of()));
        ProjectInfo info = projectService.getProjectById(5L);
        assertEquals(5L, info.id());
    }

    @Test
    void joinToProject_ok() {
        UserEntity user = user(2L);
        ProjectEntity p = project(10L);
        when(commonHelper.getProject(10L)).thenReturn(p);

        ApiResponse response = projectService.joinToProject(user, 10L);
        assertNotNull(response);
        assertEquals(1, user.getCurrentAmountProject());
    }
}
