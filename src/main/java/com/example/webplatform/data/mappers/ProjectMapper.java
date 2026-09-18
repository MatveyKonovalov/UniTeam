package com.example.webplatform.data.mappers;

import com.example.models.projects.ProjectRole;
import com.example.models.projects.ProjectCommonInfo;
import com.example.models.projects.ProjectProfessionalInfo;
import com.example.models.users.UserCommonInfo;
import com.example.models.users.UserCommonInfoWithRoles;
import com.example.webplatform.data.entities.ProjectEntity;

import java.util.stream.Collectors;

public class ProjectMapper {
    public  ProjectCommonInfo toProjectCommonInfoFromProjectEntity(ProjectEntity projectEntity) {
        return new ProjectCommonInfo(
                projectEntity.getProjectId(),
                projectEntity.getName(),
                projectEntity.getDescription(),
                projectEntity.getStars(),
                projectEntity.getUniversity(),
                projectEntity.getProjectType()
        );
    }

    public ProjectProfessionalInfo toProjectProfessionalInfoFromProjectEntity(ProjectEntity projectEntity) {
        return new ProjectProfessionalInfo(
                projectEntity.getProjectId(),
                projectEntity.getSkills(),
                projectEntity.getUsersLinks().parallelStream().map(userProjectEntity ->
                {
                    UserCommonInfo userCommonInfo = UserMapper.toUserCommonInfoFromUserEntity(userProjectEntity.getUserEntity());
                    ProjectRole projectRole = userProjectEntity.getProjectRole();
                    return new UserCommonInfoWithRoles(userCommonInfo, projectRole);
                }).collect(Collectors.toSet())
        );
    }

}
