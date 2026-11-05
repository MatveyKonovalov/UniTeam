package ru.uniteam.webplatform.data.services.users;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.uniteam.models.Skill;
import ru.uniteam.models.University;
import ru.uniteam.models.users.ProjectRole;
import ru.uniteam.webplatform.data.entities.ProjectEntity;
import ru.uniteam.webplatform.data.entities.UserEntity;
import ru.uniteam.webplatform.data.entities.UserProjectEntity;
import ru.uniteam.webplatform.data.entities.dto.users.GetUser;
import ru.uniteam.webplatform.data.entities.dto.users.GetUserContainer;
import ru.uniteam.webplatform.data.entities.usecases.DeleteUserInProject;
import ru.uniteam.webplatform.data.repositories.ProjectRepository;
import ru.uniteam.webplatform.data.repositories.UserProjectRepository;
import ru.uniteam.webplatform.data.repositories.UserRepository;
import ru.uniteam.webplatform.data.services.CommonHelper;
import ru.uniteam.webplatform.data.services.projects.ProjectMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static ru.uniteam.webplatform.TestDataFactory.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock UserRepository repository;
    @Mock UserMapper mapper;
    @Mock UserProjectRepository userProjectRepository;
    @Mock ProjectRepository projectRepository;
    @Mock ProjectMapper projectMapper;
    @Mock CommonHelper commonHelper;
    @Mock DeleteUserInProject deleteUserInProject;

    @InjectMocks UserService userService;

    @Test
    void getUsers_byUniversityAndCourse() {
        UserEntity u = user(1L);
        GetUser dto = new GetUser(1L, "Ivan", "Ivanov", "a@b.com", University.MSU, 3, List.of(), 0);
        when(repository.findByUniversityAndCourse(University.MSU, 3)).thenReturn(List.of(u));
        when(mapper.toGetUserFromUserEntity(List.of(u))).thenReturn(List.of(dto));

        GetUserContainer result = userService.getUsers(University.MSU, 3);
        assertEquals(1, result.users().size());
        verify(repository).findByUniversityAndCourse(University.MSU, 3);
    }

    @Test
    void getUsers_all() {
        when(repository.findAll()).thenReturn(List.of());
        when(mapper.toGetUserFromUserEntity(List.of())).thenReturn(List.of());
        GetUserContainer result = userService.getUsers(null, null);
        assertNotNull(result);
        verify(repository).findAll();
    }

    @Test
    void findUserById() {
        UserEntity u = user(5L);
        GetUser dto = new GetUser(5L, "Ivan", "Ivanov", "a@b.com", University.MSU, 3, List.of(), 0);
        when(commonHelper.getUser(5L)).thenReturn(u);
        when(mapper.toGetUserFromUserEntity(u)).thenReturn(dto);

        GetUser result = userService.findUserById(5L);
        assertEquals(5L, result.id());
    }

    @Test
    void getUserByEmail() {
        UserEntity u = user(1L, "x@y.com");
        when(repository.findUserEntitiesByEmail("x@y.com")).thenReturn(Optional.of(u));
        assertSame(u, userService.getUserByEmail("x@y.com"));
    }

    @Test
    void getUserByEmail_notFound_throws() {
        when(repository.findUserEntitiesByEmail("no@one.com")).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> userService.getUserByEmail("no@one.com"));
    }

    @Test
    void addSkill_ok() {
        UserEntity u = user(1L);
        GetUser dto = new GetUser(1L, "Ivan", "Ivanov", "a@b.com", University.MSU, 3, List.of(Skill.JAVA), 0);
        when(mapper.toGetUserFromUserEntity(u)).thenReturn(dto);
        GetUser result = userService.addSkill(u, Skill.PYTHON);
        assertNotNull(result);
        assertTrue(u.getSkills().contains(Skill.PYTHON));
    }

    @Test
    void deleteSkill() {
        UserEntity u = user(1L);
        u.addSkill(Skill.PYTHON);
        GetUser dto = new GetUser(1L, "Ivan", "Ivanov", "a@b.com", University.MSU, 3, List.of(), 0);
        when(mapper.toGetUserFromUserEntity(u)).thenReturn(dto);
        userService.deleteSkill(u, Skill.PYTHON);
        assertFalse(u.getSkills().contains(Skill.PYTHON));
    }

    @Test
    void giveStarProject() {
        UserEntity executor = user(1L);
        ProjectEntity p = project(10L);
        when(commonHelper.getProject(10L)).thenReturn(p);
        userService.giveStarProject(executor, 10L);
        assertTrue(executor.getProjectsStars().contains(p));
    }

    @Test
    void deleteStarProject() {
        UserEntity executor = user(1L);
        ProjectEntity p = project(10L);
        executor.addProjectStar(p);
        when(commonHelper.getProject(10L)).thenReturn(p);
        userService.deleteStarProject(executor, 10L);
        assertFalse(executor.getProjectsStars().contains(p));
    }

    @Test
    void leaveTheProject_onlyMember_deletesProject() {
        UserEntity user = user(1L);
        ProjectEntity p = project(10L);
        UserProjectEntity up = userProject(user, p, ProjectRole.CAPTAIN);
        when(commonHelper.getProject(10L)).thenReturn(p);
        when(userProjectRepository.findEmployeesByProjectEntityId(p)).thenReturn(List.of(up));

        userService.leaveTheProject(user, 10L);

        verify(deleteUserInProject).deleteUserFromProject(up);
        verify(projectRepository).delete(p);
    }

    @Test
    void leaveTheProject_captainWithSub_promotesSub() {
        UserEntity captain = user(1L);
        UserEntity sub = user(2L);
        ProjectEntity p = project(10L);
        UserProjectEntity captainUp = userProject(captain, p, ProjectRole.CAPTAIN);
        UserProjectEntity subUp = userProject(sub, p, ProjectRole.SUBCAPTAIN);
        when(commonHelper.getProject(10L)).thenReturn(p);
        when(userProjectRepository.findEmployeesByProjectEntityId(p))
                .thenReturn(List.of(captainUp, subUp));

        userService.leaveTheProject(captain, 10L);

        verify(deleteUserInProject).deleteUserFromProject(captainUp);
        assertEquals(ProjectRole.CAPTAIN, subUp.getProjectRole());
        verify(projectRepository, never()).delete(any());
    }

    @Test
    void deleteUserById() {
        userService.deleteUserById(7L);
        verify(repository).deleteById(7L);
    }
}
