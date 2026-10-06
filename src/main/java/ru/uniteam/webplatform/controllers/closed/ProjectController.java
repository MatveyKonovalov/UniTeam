package ru.uniteam.webplatform.controllers.closed;

import ru.uniteam.models.University;
import ru.uniteam.webplatform.controllers.CommonPrefix;
import ru.uniteam.webplatform.data.entities.UserEntity;
import ru.uniteam.webplatform.data.entities.dto.projects.ProjectAllInfo;
import ru.uniteam.webplatform.data.entities.dto.projects.PostProject;
import ru.uniteam.webplatform.data.entities.dto.projects.ProjectContainer;
import ru.uniteam.webplatform.data.services.projects.ProjectService;
import ru.uniteam.webplatform.data.services.users.UserService;
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
