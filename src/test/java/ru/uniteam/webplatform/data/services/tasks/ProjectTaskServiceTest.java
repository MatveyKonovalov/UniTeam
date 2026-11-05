package ru.uniteam.webplatform.data.services.tasks;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.uniteam.models.exceptions.PermissionException;
import ru.uniteam.models.tasks.TaskStatus;
import ru.uniteam.models.tasks.TaskType;
import ru.uniteam.models.users.ProjectRole;
import ru.uniteam.webplatform.data.entities.ProjectEntity;
import ru.uniteam.webplatform.data.entities.ProjectTaskEntity;
import ru.uniteam.webplatform.data.entities.UserEntity;
import ru.uniteam.webplatform.data.entities.UserProjectEntity;
import ru.uniteam.webplatform.data.entities.dto.tasks.ProjectTaskGet;
import ru.uniteam.webplatform.data.entities.dto.tasks.ProjectTaskPost;
import ru.uniteam.webplatform.data.repositories.ProjectTaskRepository;
import ru.uniteam.webplatform.data.repositories.UserProjectRepository;
import ru.uniteam.webplatform.data.services.CommonHelper;
import ru.uniteam.webplatform.data.services.projects.ProjectMapper;
import ru.uniteam.webplatform.data.services.users.UserMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static ru.uniteam.webplatform.TestDataFactory.*;

@ExtendWith(MockitoExtension.class)
class ProjectTaskServiceTest {

    @Mock ProjectTaskRepository projectTaskRepository;
    @Mock CommonHelper commonHelper;
    @Mock ProjectTaskMapper projectTaskMapper;
    @Mock UserMapper userMapper;
    @Mock ProjectMapper projectMapper;
    @Mock UserProjectRepository userProjectRepository;

    @InjectMocks ProjectTaskService service;

    @Test
    void giveNewTask_ok() {
        UserEntity captain = user(1L);
        UserEntity employee = user(2L);
        ProjectEntity project = project(10L);
        ProjectTaskPost post = new ProjectTaskPost("T", "D", TaskType.BACKEND, 10L, 2L, LocalDateTime.now().plusDays(1));
        ProjectTaskEntity entity = task(100L, employee, project);
        ProjectTaskGet getDto = new ProjectTaskGet(100L, "T", "D", TaskStatus.ACTIVE, TaskType.BACKEND, 10L, 2L, post.deadLine());

        UserProjectEntity captainUp = userProject(captain, project, ProjectRole.CAPTAIN);
        UserProjectEntity empUp = userProject(employee, project, ProjectRole.EMPLOYEE);
        List<UserProjectEntity> members = List.of(captainUp, empUp);

        when(commonHelper.getMembers(10L)).thenReturn(members);
        when(commonHelper.getUserFromMembersById(1L, members)).thenReturn(captainUp);
        when(projectTaskMapper.toProjectTaskEntityFromPost(post, employee, project)).thenReturn(entity);
        when(projectTaskRepository.save(entity)).thenReturn(entity);
        when(projectTaskMapper.toProjectTaskGetFromEntity(entity)).thenReturn(getDto);

        ProjectTaskGet result = service.giveNewTask(captain, 2L, 10L, post);
        assertEquals(100L, result.id());
        verify(projectTaskRepository).save(entity);
    }

    @Test
    void giveNewTask_notCaptain_throws() {
        UserEntity employee = user(2L);
        ProjectEntity project = project(10L);
        ProjectTaskPost post = new ProjectTaskPost("T", "D", TaskType.BACKEND, 10L, 2L, LocalDateTime.now().plusDays(1));
        UserProjectEntity empUp = userProject(employee, project, ProjectRole.EMPLOYEE);
        List<UserProjectEntity> members = List.of(empUp);

        when(commonHelper.getMembers(10L)).thenReturn(members);
        when(commonHelper.getUserFromMembersById(2L, members)).thenReturn(empUp);

        assertThrows(PermissionException.class, () -> service.giveNewTask(employee, 2L, 10L, post));
    }

    @Test
    void sendTaskOnReview_ok() {
        UserEntity owner = user(1L);
        ProjectEntity project = project(10L);
        ProjectTaskEntity task = task(5L, owner, project);
        when(projectTaskRepository.findById(5L)).thenReturn(Optional.of(task));
        when(projectTaskMapper.toProjectTaskGetFromEntity(task))
                .thenReturn(new ProjectTaskGet(5L, "Task title", "Task desc", TaskStatus.ON_REVIEW, TaskType.BACKEND, 10L, 1L, task.getDeadLine()));

        ProjectTaskGet result = service.sendTaskOnReview(owner, 5L);
        assertEquals(TaskStatus.ON_REVIEW, task.getTaskStatus());
        assertEquals(5L, result.id());
    }

    @Test
    void sendTaskOnReview_wrongUser_throws() {
        UserEntity owner = user(1L);
        UserEntity other = user(2L);
        ProjectEntity project = project(10L);
        ProjectTaskEntity task = task(5L, owner, project);
        when(projectTaskRepository.findById(5L)).thenReturn(Optional.of(task));

        assertThrows(PermissionException.class, () -> service.sendTaskOnReview(other, 5L));
    }

    @Test
    void applyTask_ok() {
        UserEntity owner = user(1L);
        ProjectEntity project = project(10L);
        ProjectTaskEntity task = task(5L, owner, project);
        when(projectTaskRepository.findById(5L)).thenReturn(Optional.of(task));
        when(projectTaskMapper.toProjectTaskGetFromEntity(task))
                .thenReturn(new ProjectTaskGet(5L, "Task title", "Task desc", TaskStatus.IN_PROGRESS, TaskType.BACKEND, 10L, 1L, task.getDeadLine()));

        ProjectTaskGet result = service.applyTask(owner, 5L);
        assertEquals(TaskStatus.IN_PROGRESS, task.getTaskStatus());
        assertEquals(5L, result.id());
    }

    @Test
    void completeTask_captainReviewsEmployee() {
        UserEntity captain = user(1L);
        UserEntity employee = user(2L);
        ProjectEntity project = project(10L);
        ProjectTaskEntity task = task(5L, employee, project);
        task.setTaskStatus(TaskStatus.ON_REVIEW);

        when(projectTaskRepository.findById(5L)).thenReturn(Optional.of(task));
        when(userProjectRepository.findUserInProject(captain, project))
                .thenReturn(Optional.of(userProject(captain, project, ProjectRole.CAPTAIN)));
        when(userProjectRepository.findUserInProject(employee, project))
                .thenReturn(Optional.of(userProject(employee, project, ProjectRole.EMPLOYEE)));
        when(projectTaskMapper.toProjectTaskGetFromEntity(task))
                .thenReturn(new ProjectTaskGet(5L, "Task title", "Task desc", TaskStatus.COMPLETED, TaskType.BACKEND, 10L, 2L, task.getDeadLine()));

        ProjectTaskGet result = service.completeTask(captain, 5L);
        assertEquals(TaskStatus.COMPLETED, task.getTaskStatus());
        assertEquals(5L, result.id());
    }
}
