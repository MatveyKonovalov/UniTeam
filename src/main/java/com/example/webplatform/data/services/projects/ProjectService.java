package com.example.webplatform.data.services.projects;

import com.example.models.University;
import com.example.models.users.ProjectRole;
import com.example.webplatform.data.entities.ProjectEntity;
import com.example.webplatform.data.entities.UserEntity;
import com.example.webplatform.data.entities.dto.projects.PostProject;
import com.example.webplatform.data.entities.dto.projects.ProjectAllInfo;
import com.example.webplatform.data.entities.dto.projects.ProjectContainer;
import com.example.webplatform.data.repositories.ProjectRepository;
import com.example.webplatform.data.repositories.UserProjectRepository;
import com.example.webplatform.data.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper mapper;

    public ProjectAllInfo addNewProject(UserEntity captain, PostProject project) {
        if (captain.canBorrowNewProject()) {
            ProjectEntity projectEntity = mapper.toProjectEntityFromPostProject(project, captain.getUniversity());

            captain.addProjectEntity(projectEntity, ProjectRole.CAPTAIN);
            projectRepository.save(projectEntity);

            return new ProjectAllInfo(mapper.toGetProjectFromProjectEntity(projectEntity),
                    captain.getEmail(),
                    Collections.emptyList());
        }
        throw new IllegalArgumentException("Too many projects");
    }

    public ProjectContainer getProjects(University university, Integer minCourse) {
        List<ProjectEntity> projects;
        if (university == null && minCourse == null) {
            projects = projectRepository.findAll();
        } else if (university == null) {
            projects = projectRepository.findProjectEntitiesByMinCourse(minCourse);
        } else if (minCourse == null) {
            projects = projectRepository.findProjectEntitiesByUniversity(university);
        } else {
            projects = projectRepository.findProjectsByUniversityAndMinCourse(university, minCourse);
        }

        return new ProjectContainer(projects.stream().map(mapper::toGetProjectFromProjectEntity).toList());
    }

}
