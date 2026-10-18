package ru.uniteam.webplatform.data.services.common;

import org.springframework.stereotype.Component;
import ru.uniteam.models.projects.ProjectType;
import ru.uniteam.webplatform.data.entities.dto.common.GetProjectType;
import ru.uniteam.webplatform.data.entities.dto.common.ProjectTypeContainer;

import java.util.Arrays;

@Component
public class ProjectTypeService {

    public ProjectTypeContainer getProjectsType() {
        return new ProjectTypeContainer(Arrays.stream(ProjectType.values()).map(GetProjectType::new).toList());
    }
}
