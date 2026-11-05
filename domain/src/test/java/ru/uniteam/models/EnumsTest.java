package ru.uniteam.models;

import org.junit.jupiter.api.Test;
import ru.uniteam.models.projects.ProjectType;
import ru.uniteam.models.security.Role;
import ru.uniteam.models.tasks.TaskStatus;
import ru.uniteam.models.tasks.TaskType;
import ru.uniteam.models.users.ProjectRole;

import static org.junit.jupiter.api.Assertions.*;

class EnumsTest {

    @Test
    void projectRole_values() {
        assertEquals(5, ProjectRole.values().length);
        assertNotNull(ProjectRole.CAPTAIN);
        assertNotNull(ProjectRole.SUBCAPTAIN);
        assertNotNull(ProjectRole.EMPLOYEE);
    }

    @Test
    void taskStatus_values() {
        assertTrue(TaskStatus.values().length >= 5);
        assertNotNull(TaskStatus.ACTIVE);
        assertNotNull(TaskStatus.COMPLETED);
    }

    @Test
    void taskType_getTitle() {
        assertEquals("Бекенд", TaskType.BACKEND.getTitle());
        assertEquals("Фронтенд", TaskType.FRONTEND.getTitle());
    }

    @Test
    void projectType_getTitle() {
        assertEquals("Разработка", ProjectType.DEVELOPMENT.getTitle());
        assertEquals("Машинное обучение", ProjectType.ML.getTitle());
    }

    @Test
    void role_values() {
        assertNotNull(Role.USER);
        assertNotNull(Role.ADMIN);
    }
}
