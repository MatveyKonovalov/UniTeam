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
import ru.uniteam.models.tasks.TaskStatus;
import ru.uniteam.models.tasks.TaskType;
import ru.uniteam.webplatform.data.entities.UserEntity;
import ru.uniteam.webplatform.data.entities.dto.tasks.ProjectTaskGet;
import ru.uniteam.webplatform.data.entities.dto.tasks.ProjectTaskPost;
import ru.uniteam.webplatform.data.services.tasks.ProjectTaskService;
import ru.uniteam.webplatform.data.services.users.UserService;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ru.uniteam.webplatform.TestDataFactory.user;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @Mock ProjectTaskService projectTaskService;
    @Mock UserService userService;
    @Mock SecurityContext securityContext;
    @Mock Authentication authentication;

    TaskController controller;
    UserEntity currentUser;

    @BeforeEach
    void setUp() {
        controller = new TaskController(projectTaskService, userService);
        currentUser = user(1L, "user@example.com");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("user@example.com");
        SecurityContextHolder.setContext(securityContext);
        when(userService.getUserByEmail("user@example.com")).thenReturn(currentUser);
    }

    @Test
    void giveTask() {
        ProjectTaskPost post = new ProjectTaskPost("T", "D", TaskType.BACKEND, 10L, 2L, LocalDateTime.now().plusDays(1));
        ProjectTaskGet get = new ProjectTaskGet(1L, "T", "D", TaskStatus.ACTIVE, TaskType.BACKEND, 10L, 2L, post.deadLine());
        when(projectTaskService.giveNewTask(currentUser, 2L, 10L, post)).thenReturn(get);

        ResponseEntity<ProjectTaskGet> response = controller.giveTask(2L, 10L, post);
        assertEquals(201, response.getStatusCode().value());
        assertEquals(get, response.getBody());
    }

    @Test
    void applyTask() {
        ProjectTaskGet get = new ProjectTaskGet(5L, "T", "D", TaskStatus.IN_PROGRESS, TaskType.BACKEND, 10L, 1L, LocalDateTime.now());
        when(projectTaskService.applyTask(currentUser, 5L)).thenReturn(get);
        ResponseEntity<ProjectTaskGet> response = controller.applyTask(5L);
        assertEquals(get, response.getBody());
    }

    @Test
    void sendTaskOnReview() {
        ProjectTaskGet get = new ProjectTaskGet(5L, "T", "D", TaskStatus.ON_REVIEW, TaskType.BACKEND, 10L, 1L, LocalDateTime.now());
        when(projectTaskService.sendTaskOnReview(currentUser, 5L)).thenReturn(get);
        assertEquals(get, controller.sendTaskOnReview(5L).getBody());
    }

    @Test
    void completeTask() {
        ProjectTaskGet get = new ProjectTaskGet(5L, "T", "D", TaskStatus.COMPLETED, TaskType.BACKEND, 10L, 1L, LocalDateTime.now());
        when(projectTaskService.completeTask(currentUser, 5L)).thenReturn(get);
        assertEquals(get, controller.completeTask(5L).getBody());
    }
}
