package com.example.webplatform.data.services.projects;

import com.example.models.University;
import com.example.webplatform.data.entities.ProjectEntity;
import com.example.webplatform.data.entities.UserProjectEntity;
import com.example.webplatform.data.entities.dto.projects.ProjectInfo;
import com.example.webplatform.data.entities.dto.projects.PostProject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ProjectMapper {
    public ProjectEntity toProjectEntityFromPostProject(PostProject project, University university){
        return new ProjectEntity(
                project.getTitle(),
                project.getDescription(),
                0,
                university,
                project.getProjectType(),
                project.getMinCourse()
        );
    }

    public ProjectInfo toGetProjectFromProjectEntity(ProjectEntity projectEntity){
        return new ProjectInfo(
                projectEntity.getProjectId(),
                projectEntity.getName(),
                projectEntity.getDescription(),
                projectEntity.getUniversity(),
                projectEntity.getMinCourse(),
                projectEntity.getStars(),
                projectEntity.getProjectType(),
                new ArrayList<>(projectEntity.getSkills())
        );
    }
}
