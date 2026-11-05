package ru.uniteam.webplatform.data.services.projects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.uniteam.models.University;
import ru.uniteam.models.projects.ProjectType;
import ru.uniteam.webplatform.data.entities.ProjectEntity;
import ru.uniteam.webplatform.data.entities.dto.projects.PostProject;
import ru.uniteam.webplatform.data.entities.dto.projects.ProjectInfo;
import ru.uniteam.webplatform.data.entities.dto.tasks.userinfo.PrTaskInfo;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.uniteam.webplatform.TestDataFactory.project;

class ProjectMapperTest {

    private ProjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ProjectMapper();
    }

    @Test
    void toProjectEntityFromPostProject() {
        PostProject post = new PostProject("Title", "Desc", ProjectType.DEVELOPMENT, List.of(), 2);
        ProjectEntity entity = mapper.toProjectEntityFromPostProject(post, University.ITMO);
        assertEquals("Title", entity.getName());
        assertEquals("Desc", entity.getDescription());
        assertEquals(University.ITMO, entity.getUniversity());
        assertEquals(ProjectType.DEVELOPMENT, entity.getProjectType());
        assertEquals(2, entity.getMinCourse());
        assertEquals(0, entity.getStars());
    }

    @Test
    void toGetProjectFromProjectEntity() {
        ProjectEntity entity = project(42L, University.MSU);
        ProjectInfo info = mapper.toGetProjectFromProjectEntity(entity);
        assertEquals(42L, info.id());
        assertEquals("Test Project", info.title());
        assertEquals(University.MSU, info.university());
        assertEquals(ProjectType.DEVELOPMENT, info.theme());
    }

    @Test
    void toPrTaskInfoFromProjectEntity() {
        ProjectEntity entity = project(7L);
        PrTaskInfo info = mapper.toPrTaskInfoFromProjectEntity(entity);
        assertEquals(7L, info.id());
        assertEquals("Test Project", info.title());
    }
}
