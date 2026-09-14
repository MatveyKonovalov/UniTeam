package com.example.models.professionalInfo;

import com.example.models.Skill;
import com.example.models.commoninfo.UserCommonInfo;

import java.util.Set;

public record ProjectProfessionalInfo(
        Long projectId,
        Set<Skill> projectStack,
        Set<UserCommonInfo> users
) {
}
