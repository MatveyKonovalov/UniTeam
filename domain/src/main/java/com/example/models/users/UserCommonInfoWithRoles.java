package com.example.models.users;

import com.example.models.projects.ProjectRole;

public record UserCommonInfoWithRoles(UserCommonInfo userCommonInfo, ProjectRole projectRole) {
}
