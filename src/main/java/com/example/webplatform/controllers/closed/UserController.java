package com.example.webplatform.controllers.closed;


import com.example.models.Skill;
import com.example.models.University;
import com.example.webplatform.controllers.CommonPrefix;
import com.example.webplatform.data.entities.UserEntity;
import com.example.webplatform.data.entities.dto.security.ApiResponse;
import com.example.webplatform.data.entities.dto.users.GetUser;
import com.example.webplatform.data.services.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(CommonPrefix.PREFIX + "users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<?> info() {
        UserEntity current = getUserFromRequest();
        return ResponseEntity.ok(userService.findUserById(current.getId()));
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

    @PostMapping("/delete_skill/{skill}")
    public GetUser removeSkill(@PathVariable Skill skill) {
        return userService.deleteSkill(getUserFromRequest(), skill);
    }

    @PostMapping("/set_university/{un}")
    public GetUser setUniversity(@PathVariable University un) {
        return userService.setUniversity(getUserFromRequest(), un);
    }

    @PostMapping("/set_course/{course}")
    public GetUser setCourse(@PathVariable int course) {
        UserEntity user = getUserFromRequest();
        return userService.setCourse(user, course);
    }

    private UserEntity getUserFromRequest() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.getUserByEmail(email);
    }

    private boolean checkRights(Long idDB, Long idNet) {
        return idDB.equals(idNet);
    }
}
