package com.example.models.professionalInfo;

import com.example.models.Skill;
import com.example.models.commoninfo.ProjectCommonInfo;

import java.util.Set;

public record UserProfessionalInfo(
        Long userId,
        Set<Skill> userSkills,
        Set<ProjectCommonInfo> projects
) {
}
