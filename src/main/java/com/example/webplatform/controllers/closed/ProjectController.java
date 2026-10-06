package com.example.webplatform.controllers.closed;

import com.example.models.University;
import com.example.webplatform.controllers.CommonPrefix;
import com.example.webplatform.data.entities.UserEntity;
import com.example.webplatform.data.entities.dto.projects.ProjectAllInfo;
import com.example.webplatform.data.entities.dto.projects.PostProject;
import com.example.webplatform.data.entities.dto.projects.ProjectContainer;
import com.example.webplatform.data.services.projects.ProjectService;
import com.example.webplatform.data.services.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(CommonPrefix.PREFIX + "projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    private final UserService userService;

    @PostMapping()
    public ProjectAllInfo addNewProject(@RequestBody PostProject project) {
        UserEntity currentUser = getUserFromRequest();

        return projectService.addNewProject(currentUser, project);
    }

    @GetMapping()
    public ProjectContainer getProjects(@RequestParam(name = "university", required = false) University university,
                                        @RequestParam(name = "course", required = false) Integer minCourse) {
        return projectService.getProjects(university, minCourse);
    }

    private UserEntity getUserFromRequest() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.getUserByEmail(email);
    }
}
