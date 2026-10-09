package ru.uniteam.webplatform.controllers.closed;


import lombok.RequiredArgsConstructor;
import ru.uniteam.models.Skill;
import ru.uniteam.models.University;
import ru.uniteam.models.exceptions.UserHasTooManyProjectsException;
import ru.uniteam.webplatform.controllers.CommonPrefix;
import ru.uniteam.webplatform.data.entities.UserEntity;
import ru.uniteam.webplatform.data.entities.dto.security.ApiResponse;
import ru.uniteam.webplatform.data.entities.dto.users.GetUser;
import ru.uniteam.webplatform.data.services.projects.ProjectService;
import ru.uniteam.webplatform.data.services.users.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(CommonPrefix.PREFIX + "users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ProjectService projectService;

    @GetMapping()
    public ResponseEntity<?> info() {
        UserEntity current = getUserFromRequest();
        try {
            return ResponseEntity.ok(userService.findUserById(current.getId()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(new ApiResponse(e.getMessage()));
        }
    }

    @PostMapping("/add_skill/{skill}")
    public ResponseEntity<GetUser> addSkill(@PathVariable Skill skill) {
        UserEntity current = getUserFromRequest();
        return ResponseEntity.ok(userService.addSkill(current, skill));
    }

    @DeleteMapping("/delete_skill/{skill}")
    public GetUser removeSkill(@PathVariable Skill skill) {
        return userService.deleteSkill(getUserFromRequest(), skill);
    }

    @PostMapping("/set_university/{un}")
    public GetUser setUniversity(@PathVariable University un) {
        return userService.setUniversity(getUserFromRequest(), un);
    }

    @PostMapping("/set_course/{course}")
    public ResponseEntity<GetUser> setCourse(@PathVariable int course) {
        UserEntity user = getUserFromRequest();
        return ResponseEntity.ok(userService.setCourse(user, course));
    }

    @PostMapping("/join_project/{projectId}")
    public ResponseEntity<ApiResponse> joinProject(@PathVariable long projectId) {
        UserEntity current = getUserFromRequest();
        return ResponseEntity.ok(projectService.joinToProject(current, projectId));
    }

    @DeleteMapping("/leave_project/{projectId}")
    public ResponseEntity<ApiResponse> leaveTheProject(@PathVariable("projectId") long projectId) {
        userService.leaveTheProject(getUserFromRequest(), projectId);
        return ResponseEntity.ok(new ApiResponse("User leaved from project with id=" + projectId));
    }

    private UserEntity getUserFromRequest() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.getUserByEmail(email);
    }
}
