package com.example.webplatform.data.entities.dto.projects;

import com.example.models.Skill;
import com.example.models.University;
import com.example.models.projects.ProjectType;

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
