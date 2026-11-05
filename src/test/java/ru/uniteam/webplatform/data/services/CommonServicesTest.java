package ru.uniteam.webplatform.data.services;

import org.junit.jupiter.api.Test;
import ru.uniteam.models.Skill;
import ru.uniteam.models.University;
import ru.uniteam.models.projects.ProjectType;
import ru.uniteam.webplatform.data.entities.dto.common.GetCommonInfo;
import ru.uniteam.webplatform.data.entities.dto.common.GetContainerCommon;
import ru.uniteam.webplatform.data.entities.dto.common.ProjectTypeContainer;
import ru.uniteam.webplatform.data.services.common.ProjectTypeService;
import ru.uniteam.webplatform.data.services.common.SkillService;
import ru.uniteam.webplatform.data.services.common.UniversityService;

import static org.junit.jupiter.api.Assertions.*;

class CommonServicesTest {

    private final UniversityService universityService = new UniversityService();
    private final SkillService skillService = new SkillService();
    private final ProjectTypeService projectTypeService = new ProjectTypeService();

    @Test
    void universityService_returnsAllUniversities() {
        GetContainerCommon container = universityService.getContainerUniversity();
        assertNotNull(container);
        assertNotNull(container.elements());
        assertEquals(University.values().length, container.elements().size());
        GetCommonInfo first = container.elements().get(0);
        assertTrue(first.id() > 0);
        assertNotNull(first.title());
    }

    @Test
    void skillService_returnsAllSkills() {
        GetContainerCommon container = skillService.getSkills();
        assertNotNull(container);
        assertEquals(Skill.values().length, container.elements().size());
        assertTrue(container.elements().stream().anyMatch(s -> s.title().equals("Java")));
    }

    @Test
    void projectTypeService_returnsAllTypes() {
        ProjectTypeContainer container = projectTypeService.getProjectsType();
        assertNotNull(container);
        assertEquals(ProjectType.values().length, container.types().size());
    }
}
