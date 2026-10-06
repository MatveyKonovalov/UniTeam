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
    public ResponseEntity<?> addSkill(@PathVariable Skill skill) {
        UserEntity current = getUserFromRequest();
        try {
            return ResponseEntity.ok(userService.addSkill(current, skill));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage()));
        }

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
    public ResponseEntity<?> setCourse(@PathVariable int course) {
        UserEntity user = getUserFromRequest();
        try {
            return ResponseEntity.ok(userService.setCourse(user, course));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage()));
        }
    }

    @PostMapping("/join_project/{projectId}")
    public ResponseEntity<?> joinProject(@PathVariable long projectId) {
        UserEntity current = getUserFromRequest();
        try {
            return ResponseEntity.ok(projectService.joinToProject(current, projectId));
        } catch (IllegalArgumentException | UserHasTooManyProjectsException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage()));
        }
    }

    @DeleteMapping("/leave_project/{projectId}")
    public void leaveTheProject(@PathVariable("projectId") long projectId) {
        userService.leaveTheProject(getUserFromRequest(), projectId);
    }

    private UserEntity getUserFromRequest() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.getUserByEmail(email);
    }
}
