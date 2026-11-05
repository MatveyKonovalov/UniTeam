package ru.uniteam.webplatform.controllers.opened;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import ru.uniteam.models.Skill;
import ru.uniteam.models.University;
import ru.uniteam.webplatform.data.entities.dto.common.GetCommonInfo;
import ru.uniteam.webplatform.data.entities.dto.common.GetContainerCommon;
import ru.uniteam.webplatform.data.entities.dto.common.ProjectTypeContainer;
import ru.uniteam.webplatform.data.entities.dto.security.ApiResponse;
import ru.uniteam.webplatform.data.services.common.ProjectTypeService;
import ru.uniteam.webplatform.data.services.common.SkillService;
import ru.uniteam.webplatform.data.services.common.UniversityService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommonInfoTest {

    @Mock UniversityService universityService;
    @Mock SkillService skillService;
    @Mock ProjectTypeService projectTypeService;

    CommonInfo controller;

    @BeforeEach
    void setUp() {
        controller = new CommonInfo(universityService, skillService, projectTypeService);
    }

    @Test
    void getUniversities() {
        GetContainerCommon container = new GetContainerCommon(List.of());
        when(universityService.getContainerUniversity()).thenReturn(container);
        assertSame(container, controller.getUniversities());
    }

    @Test
    void getUniversityInfoById_found() {
        ResponseEntity<?> response = controller.getUniversityInfoById(1);
        assertEquals(200, response.getStatusCode().value());
        assertInstanceOf(GetCommonInfo.class, response.getBody());
    }

    @Test
    void getUniversityInfoById_notFound() {
        ResponseEntity<?> response = controller.getUniversityInfoById(99999);
        assertEquals(404, response.getStatusCode().value());
        assertInstanceOf(ApiResponse.class, response.getBody());
    }

    @Test
    void getSkills() {
        GetContainerCommon container = new GetContainerCommon(List.of());
        when(skillService.getSkills()).thenReturn(container);
        assertSame(container, controller.getSkills());
    }

    @Test
    void getSkillById_found() {
        ResponseEntity<?> response = controller.getSkillById(Skill.JAVA.getId());
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void getSkillById_notFound() {
        ResponseEntity<?> response = controller.getSkillById(-1);
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void getProjectTypes() {
        ProjectTypeContainer container = new ProjectTypeContainer(List.of());
        when(projectTypeService.getProjectsType()).thenReturn(container);
        assertSame(container, controller.getProjectTypes());
    }
}
