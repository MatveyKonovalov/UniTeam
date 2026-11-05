package ru.uniteam.webplatform.data.services.tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.uniteam.models.tasks.TaskStatus;
import ru.uniteam.models.tasks.TaskType;
import ru.uniteam.webplatform.data.entities.ProjectEntity;
import ru.uniteam.webplatform.data.entities.ProjectTaskEntity;
import ru.uniteam.webplatform.data.entities.UserEntity;
import ru.uniteam.webplatform.data.entities.dto.tasks.ProjectTaskGet;
import ru.uniteam.webplatform.data.entities.dto.tasks.ProjectTaskInfo;
import ru.uniteam.webplatform.data.entities.dto.tasks.ProjectTaskPost;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static ru.uniteam.webplatform.TestDataFactory.*;

class ProjectTaskMapperTest {

    private ProjectTaskMapper mapper;
    private UserEntity user;
    private ProjectEntity project;

    @BeforeEach
    void setUp() {
        mapper = new ProjectTaskMapper();
        user = user(1L);
        project = project(10L);
    }

    @Test
    void toProjectTaskEntityFromPost_ok() {
        LocalDateTime deadline = LocalDateTime.now().plusDays(3);
        ProjectTaskPost post = new ProjectTaskPost("T", "D", TaskType.BACKEND, 10L, 1L, deadline);
        ProjectTaskEntity entity = mapper.toProjectTaskEntityFromPost(post, user, project);
        assertEquals("T", entity.getTitle());
        assertEquals("D", entity.getDescription());
        assertEquals(TaskStatus.ACTIVE, entity.getTaskStatus());
        assertEquals(TaskType.BACKEND, entity.getTaskType());
        assertEquals(user, entity.getUserEntity());
        assertEquals(project, entity.getProjectEntity());
        assertEquals(deadline, entity.getDeadLine());
    }

    @Test
    void toProjectTaskEntityFromPost_idMismatch_throws() {
        ProjectTaskPost post = new ProjectTaskPost("T", "D", TaskType.BACKEND, 99L, 1L, LocalDateTime.now());
        assertThrows(IllegalArgumentException.class,
                () -> mapper.toProjectTaskEntityFromPost(post, user, project));
    }

    @Test
    void toProjectTaskGetFromEntity() {
        ProjectTaskEntity entity = task(5L, user, project);
        ProjectTaskGet get = mapper.toProjectTaskGetFromEntity(entity);
        assertEquals(5L, get.id());
        assertEquals("Task title", get.title());
        assertEquals(TaskStatus.ACTIVE, get.status());
        assertEquals(10L, get.projectId());
        assertEquals(1L, get.userId());
    }

    @Test
    void toProjectTaskInfo() {
        ProjectTaskEntity entity = task(3L, user, project);
        ProjectTaskInfo info = mapper.toProjectTaskInfo(entity);
        assertEquals(3L, info.id());
        assertEquals("Task title", info.title());
        assertEquals(TaskStatus.ACTIVE, info.status());
    }
}
