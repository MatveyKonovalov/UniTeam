package ru.uniteam.webplatform.data.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.uniteam.models.users.ProjectRole;
import ru.uniteam.webplatform.data.entities.ProjectEntity;
import ru.uniteam.webplatform.data.entities.UserEntity;
import ru.uniteam.webplatform.data.entities.UserProjectEntity;
import ru.uniteam.webplatform.data.repositories.ProjectRepository;
import ru.uniteam.webplatform.data.repositories.UserProjectRepository;
import ru.uniteam.webplatform.data.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ru.uniteam.webplatform.TestDataFactory.*;

@ExtendWith(MockitoExtension.class)
class CommonHelperTest {

    @Mock ProjectRepository projectRepository;
    @Mock UserRepository userRepository;
    @Mock UserProjectRepository userProjectRepository;
    @InjectMocks CommonHelper helper;

    @Test
    void getProject_found() {
        ProjectEntity p = project(1L);
        when(projectRepository.findById(1L)).thenReturn(Optional.of(p));
        assertSame(p, helper.getProject(1L));
    }

    @Test
    void getProject_notFound_throws() {
        when(projectRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> helper.getProject(99L));
    }

    @Test
    void getUser_found() {
        UserEntity u = user(5L);
        when(userRepository.findById(5L)).thenReturn(Optional.of(u));
        assertSame(u, helper.getUser(5L));
    }

    @Test
    void getUser_notFound_throws() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> helper.getUser(99L));
    }

    @Test
    void getMembers() {
        ProjectEntity p = project(1L);
        UserProjectEntity up = userProject(user(1L), p, ProjectRole.CAPTAIN);
        when(projectRepository.findById(1L)).thenReturn(Optional.of(p));
        when(userProjectRepository.findAllByProjectEntityId(p)).thenReturn(List.of(up));
        List<UserProjectEntity> members = helper.getMembers(1L);
        assertEquals(1, members.size());
        assertSame(up, members.get(0));
    }

    @Test
    void getUserFromMembersById_found() {
        UserEntity u = user(2L);
        UserProjectEntity up = userProject(u, project(1L), ProjectRole.EMPLOYEE);
        assertSame(up, helper.getUserFromMembersById(2L, List.of(up)));
    }

    @Test
    void getUserFromMembersById_notFound_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> helper.getUserFromMembersById(99L, List.of()));
    }

    @Test
    void isCaptain_true() {
        UserEntity captain = user(1L);
        UserProjectEntity up = userProject(captain, project(1L), ProjectRole.CAPTAIN);
        assertTrue(helper.isCaptain(captain, List.of(up)));
    }

    @Test
    void isCaptain_false() {
        UserEntity user = user(1L);
        UserProjectEntity up = userProject(user, project(1L), ProjectRole.EMPLOYEE);
        assertFalse(helper.isCaptain(user, List.of(up)));
    }

    @Test
    void isCaptainOrSubCaptain_trueForSub() {
        UserEntity sub = user(2L);
        UserProjectEntity up = userProject(sub, project(1L), ProjectRole.SUBCAPTAIN);
        assertTrue(helper.isCaptainOrSubCaptain(sub, List.of(up)));
    }

    @Test
    void isCaptainOrSubCaptain_falseForEmployee() {
        UserEntity emp = user(3L);
        UserProjectEntity up = userProject(emp, project(1L), ProjectRole.EMPLOYEE);
        assertFalse(helper.isCaptainOrSubCaptain(emp, List.of(up)));
    }
}
