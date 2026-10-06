package ru.uniteam.webplatform.data.entities.dto.projects;

import ru.uniteam.models.Skill;
import ru.uniteam.models.University;
import ru.uniteam.models.projects.ProjectType;

import java.util.List;

public record ProjectInfo(
        Long id,
        String title,
        String description,
        University university,
        int minCourse,
        int stars,
        ProjectType theme,
        List<Skill> stack
) { }
