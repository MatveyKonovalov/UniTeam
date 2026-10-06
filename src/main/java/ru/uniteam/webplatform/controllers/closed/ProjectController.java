package ru.uniteam.webplatform.controllers.closed;

import org.springframework.http.ResponseEntity;
import ru.uniteam.models.University;
import ru.uniteam.models.exceptions.PermissionException;
import ru.uniteam.webplatform.controllers.CommonPrefix;
import ru.uniteam.webplatform.data.entities.ProjectEntity;
import ru.uniteam.webplatform.data.entities.UserEntity;
import ru.uniteam.webplatform.data.entities.dto.projects.ProjectAllInfo;
import ru.uniteam.webplatform.data.entities.dto.projects.PostProject;
import ru.uniteam.webplatform.data.entities.dto.projects.ProjectContainer;
import ru.uniteam.webplatform.data.entities.dto.projects.ProjectInfo;
import ru.uniteam.webplatform.data.entities.dto.security.ApiResponse;
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

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getInfoAboutProject(@PathVariable long projectId) {
        try {
            return ResponseEntity.ok(projectService.getProjectById(projectId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(new ApiResponse(e.getMessage()));
        }
    }

    @GetMapping("/{projectId}/candidates")
    public ResponseEntity<?> getCandidatesByProjectId(@PathVariable long projectId) {
        try {
            return ResponseEntity.ok(projectService.getCandidates(projectId, getUserFromRequest()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(new ApiResponse(e.getMessage()));
        } catch (PermissionException e) {
            return ResponseEntity.status(403).body(new ApiResponse(e.getMessage()));
        }
    }

    @PostMapping("/{projectId}/add/{candidateId}")
    public ResponseEntity<?> addCandidate(@PathVariable long projectId, @PathVariable long candidateId) {
        try {
            projectService.addNewCandidate(projectId, candidateId, getUserFromRequest());
            return ResponseEntity.ok(new ApiResponse("User has been added"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(new ApiResponse(e.getMessage()));
        } catch (PermissionException e) {
            return ResponseEntity.status(403).body(new ApiResponse(e.getMessage()));
        }
    }

    private UserEntity getUserFromRequest() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.getUserByEmail(email);
    }
}
