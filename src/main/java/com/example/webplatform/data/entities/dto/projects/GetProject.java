package com.example.webplatform.data.entities.dto.projects;

import com.example.models.Skill;
import com.example.models.University;
import com.example.models.projects.ProjectType;

import java.util.List;

public record GetProject(
        Long id,
        String title,
        University university,
        int minCourse,
        ProjectType theme,
        List<Skill> stack
) { }
