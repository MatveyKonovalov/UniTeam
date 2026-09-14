package com.example.webplatform.data.dto;

import com.example.models.University;
import com.example.webplatform.data.entities.ProjectEntity;
import com.example.webplatform.data.entities.SkillEntity;

import java.util.List;

public record UserInfoDto(
        Integer userId,
        String firstName,
        String lastName,
        String email,
        University university,
        List<SkillEntity> skillsId,
        List<ProjectEntity> capitanProjectsId,
        List<ProjectEntity> employeeProjectsId)
{}
