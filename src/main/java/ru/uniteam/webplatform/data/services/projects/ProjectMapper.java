package ru.uniteam.webplatform.data.services.projects;

import lombok.RequiredArgsConstructor;
import ru.uniteam.models.University;
import ru.uniteam.webplatform.data.entities.ProjectEntity;
import ru.uniteam.webplatform.data.entities.dto.projects.ProjectInfo;
import ru.uniteam.webplatform.data.entities.dto.projects.PostProject;
import org.springframework.stereotype.Component;
import ru.uniteam.webplatform.data.entities.dto.tasks.userinfo.PrTaskInfo;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
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

    public PrTaskInfo toPrTaskInfoFromProjectEntity(ProjectEntity projectEntity){
        return new PrTaskInfo(projectEntity.getProjectId(), projectEntity.getName());
    }
}
