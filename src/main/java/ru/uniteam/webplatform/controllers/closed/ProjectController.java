package ru.uniteam.webplatform.controllers.closed;

import org.springframework.http.ResponseEntity;
import ru.uniteam.models.University;
import ru.uniteam.models.exceptions.PermissionException;
import ru.uniteam.webplatform.controllers.CommonPrefix;
import ru.uniteam.webplatform.data.entities.ProjectEntity;
import ru.uniteam.webplatform.data.entities.UserEntity;
import ru.uniteam.webplatform.data.entities.dto.projects.*;
import ru.uniteam.webplatform.data.entities.dto.security.ApiResponse;
import ru.uniteam.webplatform.data.entities.dto.users.GetUserContainer;
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
    public ResponseEntity<ProjectInfo> getInfoAboutProject(@PathVariable long projectId) {
        return ResponseEntity.ok(projectService.getProjectById(projectId));
    }

    @GetMapping("/{projectId}/candidates")
    public ResponseEntity<GetUserContainer> getCandidatesByProjectId(@PathVariable long projectId) {
        return ResponseEntity.ok(projectService.getCandidates(projectId, getUserFromRequest()));
    }

    @PostMapping("/{projectId}/add/{candidateId}")
    public ResponseEntity<ApiResponse> addCandidate(@PathVariable long projectId, @PathVariable long candidateId) {
        projectService.addNewCandidate(projectId, candidateId, getUserFromRequest());
        return ResponseEntity.ok(new ApiResponse("User has been added"));
    }

    @PostMapping("/{projectId}/cancel/{candidateId}")
    public ResponseEntity<ApiResponse> cancelCandidate(@PathVariable long projectId, @PathVariable long candidateId) {
        projectService.cancelProject(projectId, candidateId, getUserFromRequest());
        return ResponseEntity.ok(new ApiResponse("User has been canceled"));
    }

    @GetMapping("/{projectId}/cancelled_candidates")
    public ResponseEntity<GetUserContainer> getCancelledCandidates(@PathVariable long projectId) {
        return ResponseEntity.ok(projectService.getCancelledCandidates(projectId, getUserFromRequest()));
    }

    @PostMapping("/{projectId}/appoint_as_deputy/{employerId}")
    public ResponseEntity<ApiResponse> appointAsDeputy(@PathVariable long projectId, @PathVariable long employerId) {
        projectService.appointAsDeputy(projectId, employerId, getUserFromRequest());
        return ResponseEntity.ok(
                new ApiResponse("The user with id=" + employerId +
                        " became a deputy on the project with id=" + projectId));

    }

    @PostMapping("/{projectId}/demote_position/{employerId}")
    public ResponseEntity<ApiResponse> demotePosition(@PathVariable long projectId, @PathVariable long employerId) {
        projectService.demotePosition(projectId, employerId, getUserFromRequest());
        return ResponseEntity.ok(
                new ApiResponse("The user with id=" +
                        employerId + "has become an employee in project with id=" + projectId));
    }

    @DeleteMapping("/{projectId}/{employeeId}")
    public ResponseEntity<ApiResponse> deleteUserINProjectById(@PathVariable long projectId,
                                                               @PathVariable long employeeId){
        projectService.deleteUser(projectId, employeeId, getUserFromRequest());
        return ResponseEntity.ok(new ApiResponse("The user with id=" + employeeId
                + "has deleted from project with id=" + projectId));
    }

    @GetMapping("/{projectId}/members")
    public ResponseEntity<UserInProjectContainer> getMembers(@PathVariable long projectId){
        return ResponseEntity.ok(projectService.getMembersInProject(projectId));
    }


    private UserEntity getUserFromRequest() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.getUserByEmail(email);
    }
}
