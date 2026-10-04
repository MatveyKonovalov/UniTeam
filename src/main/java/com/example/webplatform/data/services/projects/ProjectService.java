package com.example.webplatform.data.services.projects;

import com.example.models.University;
import com.example.models.projects.ProjectType;
import com.example.models.users.ProjectRole;
import com.example.webplatform.data.entities.ProjectEntity;
import com.example.webplatform.data.entities.UserEntity;
import com.example.webplatform.data.entities.UserProjectEntity;
import com.example.webplatform.data.entities.dto.projects.GetProject;
import com.example.webplatform.data.entities.dto.projects.PostAddProject;
import com.example.webplatform.data.entities.dto.projects.ProjectContainer;
import com.example.webplatform.data.repositories.ProjectRepository;
import com.example.webplatform.data.repositories.UserProjectRepository;
import com.example.webplatform.data.repositories.UserRepository;
import com.example.webplatform.data.services.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectService {
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final UserProjectRepository userProjectRepository;
    private final ProjectMapper mapper;

    @Autowired
    public ProjectService(UserRepository userRepository,
                          ProjectRepository projectRepository,
                          UserProjectRepository userProjectRepository,
                          ProjectMapper mapper) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.userProjectRepository = userProjectRepository;
        this.mapper = mapper;
    }

    // Капитан может быть ниже курсом, чем требование проекта
    public GetProject addProject(PostAddProject projectContainer) {
        Optional<UserEntity> captain = userRepository.findById(projectContainer.userId());

        if (captain.isEmpty()) {
            throw new IllegalArgumentException("User with id=" + projectContainer.userId() + " not found");
        }

        ProjectEntity currentProject = mapper.toProjectEntityFromPostProject(projectContainer.project());

        projectRepository.save(currentProject);
        userProjectRepository.save(new UserProjectEntity(captain.get(), currentProject, ProjectRole.CAPTAIN));

        return mapper.toGetProjectFromProjectEntity(currentProject);
    }

//    public ProjectContainer getProjects(University university, int minCourse, ProjectType projectType){
//        //TODO
//    }

}
