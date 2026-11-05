package ru.uniteam.webplatform;

import ru.uniteam.models.Skill;
import ru.uniteam.models.University;
import ru.uniteam.models.projects.ProjectType;
import ru.uniteam.models.tasks.TaskStatus;
import ru.uniteam.models.tasks.TaskType;
import ru.uniteam.models.users.ProjectRole;
import ru.uniteam.webplatform.data.entities.ProjectEntity;
import ru.uniteam.webplatform.data.entities.ProjectTaskEntity;
import ru.uniteam.webplatform.data.entities.UserEntity;
import ru.uniteam.webplatform.data.entities.UserProjectEntity;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public final class TestDataFactory {

    private TestDataFactory() {}

    public static UserEntity user(long id, String email) {
        UserEntity u = new UserEntity("Ivan", "Ivanov", email, University.MSU,
                new HashSet<>(Set.of(Skill.JAVA)), 3);
        setField(u, "id", id);
        return u;
    }

    public static UserEntity user(long id) {
        return user(id, "user" + id + "@example.com");
    }

    public static ProjectEntity project(long id, University uni) {
        ProjectEntity p = new ProjectEntity("Test Project", "Description", 0, uni, ProjectType.DEVELOPMENT, 1);
        setField(p, "projectId", id);
        return p;
    }

    public static ProjectEntity project(long id) {
        return project(id, University.MSU);
    }

    public static UserProjectEntity userProject(UserEntity user, ProjectEntity project, ProjectRole role) {
        return new UserProjectEntity(user, project, role);
    }

    public static ProjectTaskEntity task(long id, UserEntity user, ProjectEntity project) {
        ProjectTaskEntity t = new ProjectTaskEntity(
                "Task title", "Task desc", TaskStatus.ACTIVE, TaskType.BACKEND,
                project, user, LocalDateTime.now().plusDays(7));
        setField(t, "id", id);
        return t;
    }

    public static void setField(Object target, String name, Object value) {
        try {
            Field f = target.getClass().getDeclaredField(name);
            f.setAccessible(true);
            f.set(target, value);
        } catch (Exception e) {
            // try superclass
            try {
                Field f = target.getClass().getSuperclass().getDeclaredField(name);
                f.setAccessible(true);
                f.set(target, value);
            } catch (Exception ex) {
                throw new RuntimeException("Cannot set field " + name, ex);
            }
        }
    }
}
