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
import ru.uniteam.models.Skill;
import ru.uniteam.models.University;
import ru.uniteam.webplatform.data.entities.UserEntity;
import ru.uniteam.webplatform.data.entities.dto.users.GetUser;
import ru.uniteam.webplatform.data.services.projects.ProjectService;
import ru.uniteam.webplatform.data.services.users.UserService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ru.uniteam.webplatform.TestDataFactory.user;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock UserService userService;
    @Mock ProjectService projectService;
    @Mock SecurityContext securityContext;
    @Mock Authentication authentication;

    UserController controller;
    UserEntity currentUser;

    @BeforeEach
    void setUp() {
        controller = new UserController(userService, projectService);
        currentUser = user(1L, "user@example.com");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("user@example.com");
        SecurityContextHolder.setContext(securityContext);
        when(userService.getUserByEmail("user@example.com")).thenReturn(currentUser);
    }

    @Test
    void info() {
        GetUser dto = new GetUser(1L, "Ivan", "Ivanov", "user@example.com", University.MSU, 3, List.of(), 0);
        when(userService.findUserById(1L)).thenReturn(dto);
        ResponseEntity<?> response = controller.info();
        assertEquals(200, response.getStatusCode().value());
        assertEquals(dto, response.getBody());
    }

    @Test
    void addSkill() {
        GetUser dto = new GetUser(1L, "Ivan", "Ivanov", "user@example.com", University.MSU, 3, List.of(Skill.JAVA), 0);
        when(userService.addSkill(currentUser, Skill.JAVA)).thenReturn(dto);
        ResponseEntity<GetUser> response = controller.addSkill(Skill.JAVA);
        assertEquals(dto, response.getBody());
    }

    @Test
    void removeSkill() {
        GetUser dto = new GetUser(1L, "Ivan", "Ivanov", "user@example.com", University.MSU, 3, List.of(), 0);
        when(userService.deleteSkill(currentUser, Skill.JAVA)).thenReturn(dto);
        assertEquals(dto, controller.removeSkill(Skill.JAVA));
    }
}
