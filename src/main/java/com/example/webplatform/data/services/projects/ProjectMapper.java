package com.example.webplatform.data.services.projects;

import com.example.webplatform.data.entities.ProjectEntity;
import com.example.webplatform.data.entities.dto.projects.GetProject;
import com.example.webplatform.data.entities.dto.projects.PostProject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ProjectMapper {
    public ProjectEntity toProjectEntityFromPostProject(PostProject project){
        return new ProjectEntity(
                project.getTitle(),
                project.getDescription(),
                0,
                project.getUniversity(),
                project.getProjectType(),
                project.getMinCourse()
        );
    }

    public GetProject toGetProjectFromProjectEntity(ProjectEntity projectEntity){
        return new GetProject(
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
