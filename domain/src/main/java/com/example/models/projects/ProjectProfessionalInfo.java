package com.example.models.projects;

import com.example.models.Skill;
import com.example.models.tasks.ProjectTask;
import com.example.models.users.UserCommonInfo;
import com.example.models.users.UserCommonInfoWithRoles;

import java.util.Set;

public record ProjectProfessionalInfo(
        Long projectId,
        Set<Skill> projectStack,
        Set<UserCommonInfoWithRoles> users,
        Set<ProjectTask> projectTasks
) {
}
