package ru.uniteam.webplatform.data.entities.dto.projects;

import java.util.List;

public record ProjectContainer(
        List<ProjectInfo> projects
) { }
