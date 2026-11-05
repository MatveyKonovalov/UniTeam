package ru.uniteam.webplatform.data.services.users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.uniteam.models.Skill;
import ru.uniteam.models.University;
import ru.uniteam.models.users.ProjectRole;
import ru.uniteam.models.users.UserData;
import ru.uniteam.webplatform.data.entities.UserEntity;
import ru.uniteam.webplatform.data.entities.dto.tasks.projectInfo.UserTaskInfo;
import ru.uniteam.webplatform.data.entities.dto.users.GetUser;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.uniteam.webplatform.TestDataFactory.user;

class UserMapperTest {

    private UserMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new UserMapper();
    }

    static class TestUserData extends UserData {
        public TestUserData(String firstName, String lastName, String email,
                            University university, int course, List<Skill> skills, String password) {
            super(firstName, lastName, email, university, course, skills, password);
        }
    }

    @Test
    void toUserEntityFromPostUser_mapsFields() {
        TestUserData data = new TestUserData("Ivan", "Ivanov", "ivan@example.com",
                University.MSU, 3, List.of(Skill.JAVA), "pass");
        UserEntity entity = mapper.toUserEntityFromPostUser(data);
        assertEquals("Ivan", entity.getFirstName());
        assertEquals("Ivanov", entity.getLastName());
        assertEquals("ivan@example.com", entity.getEmail());
        assertEquals(University.MSU, entity.getUniversity());
        assertEquals(3, entity.getCourse());
        assertTrue(entity.getSkills().contains(Skill.JAVA));
    }

    @Test
    void toGetUserFromUserEntity_mapsFields() {
        UserEntity entity = user(10L, "a@b.com");
        GetUser dto = mapper.toGetUserFromUserEntity(entity);
        assertEquals(10L, dto.id());
        assertEquals("Ivan", dto.firstName());
        assertEquals("Ivanov", dto.lastName());
        assertEquals("a@b.com", dto.email());
        assertEquals(University.MSU, dto.university());
        assertEquals(3, dto.course());
    }

    @Test
    void toGetUserFromUserEntity_list() {
        List<GetUser> list = mapper.toGetUserFromUserEntity(List.of(user(1L), user(2L)));
        assertEquals(2, list.size());
        assertEquals(1L, list.get(0).id());
        assertEquals(2L, list.get(1).id());
    }

    @Test
    void toUserTaskInfoFromEntity() {
        UserEntity entity = user(5L);
        UserTaskInfo info = mapper.toUserTaskInfoFromEntity(entity, ProjectRole.CAPTAIN);
        assertEquals("Ivan", info.firstName());
        assertEquals("Ivanov", info.lastName());
        assertEquals(entity.getEmail(), info.email());
        assertEquals(ProjectRole.CAPTAIN, info.projectRole());
    }
}
