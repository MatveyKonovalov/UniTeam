package com.example.models.users;

import com.example.models.Skill;
import com.example.models.projects.ProjectCommonInfo;

import java.util.Set;

public record UserProfessionalInfo(
        Long userId,
        Set<Skill> userSkills
) {
}