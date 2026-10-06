package ru.uniteam.webplatform.data.entities.dto.projects;

import java.util.List;

public record ProjectAllInfo(
        ProjectInfo project,
        String emailOwner,
        List<String> employees
) { }
