package ru.uniteam.webplatform.controllers.closed;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.uniteam.models.University;
import ru.uniteam.models.projects.ProjectType;
import ru.uniteam.webplatform.data.entities.UserEntity;
import ru.uniteam.webplatform.data.entities.dto.projects.*;
import ru.uniteam.webplatform.data.services.projects.ProjectService;
import ru.uniteam.webplatform.data.services.users.UserService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ru.uniteam.webplatform.TestDataFactory.user;

@ExtendWith(MockitoExtension.class)
class ProjectControllerTest {

    @Mock ProjectService projectService;
    @Mock UserService userService;
    @Mock SecurityContext securityContext;
    @Mock Authentication authentication;

    ProjectController controller;
    UserEntity currentUser;

    @BeforeEach
    void setUp() {
        controller = new ProjectController(userService, projectService);
        currentUser = user(1L, "captain@example.com");
    }

    private void mockAuth() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("captain@example.com");
        SecurityContextHolder.setContext(securityContext);
        when(userService.getUserByEmail("captain@example.com")).thenReturn(currentUser);
    }

    @Test
    void addNewProject() {
        mockAuth();
        PostProject post = new PostProject("Title", "Desc", ProjectType.DEVELOPMENT, List.of(), 1);
        ProjectAllInfo expected = new ProjectAllInfo(
                new ProjectInfo(1L, "Title", "Desc", University.MSU, 1, 0, ProjectType.DEVELOPMENT, List.of()),
                "captain@example.com", List.of());
        when(projectService.addNewProject(currentUser, post)).thenReturn(expected);

        ProjectAllInfo result = controller.addNewProject(post);
        assertSame(expected, result);
        verify(projectService).addNewProject(currentUser, post);
    }

    @Test
    void getProjects() {
        ProjectContainer container = new ProjectContainer(List.of());
        when(projectService.getProjects(University.MSU, 2)).thenReturn(container);
        assertSame(container, controller.getProjects(University.MSU, 2));
    }

    @Test
    void getInfoAboutProject() {
        ProjectInfo info = new ProjectInfo(5L, "T", "D", University.MSU, 1, 0, ProjectType.DEVELOPMENT, List.of());
        when(projectService.getProjectById(5L)).thenReturn(info);
        ResponseEntity<ProjectInfo> response = controller.getInfoAboutProject(5L);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(info, response.getBody());
    }
}
