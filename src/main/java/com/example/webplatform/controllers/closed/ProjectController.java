package com.example.webplatform.controllers.closed;

import com.example.models.University;
import com.example.models.projects.ProjectType;
import com.example.webplatform.controllers.CommonPrefix;
import com.example.webplatform.data.entities.dto.projects.GetProject;
import com.example.webplatform.data.entities.dto.projects.PostAddProject;
import com.example.webplatform.data.entities.dto.projects.ProjectContainer;
import com.example.webplatform.data.services.projects.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(CommonPrefix.PREFIX + "projects")
public class ProjectController {
    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping()
    public GetProject addProject(@RequestBody PostAddProject projectContainer) {
        return projectService.addProject(projectContainer);
    }

//    @GetMapping()
//    public ProjectContainer getProject(
//            @RequestParam(required = false) University university,
//            @RequestParam(required = false) int course,
//            @RequestParam(required = false) ProjectType type
//    ){
//
//    }
}
