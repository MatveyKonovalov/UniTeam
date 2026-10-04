package com.example.webplatform.controllers.closed;


import com.example.models.University;
import com.example.webplatform.data.entities.dto.users.GetUser;
import com.example.webplatform.data.entities.dto.users.GetUserContainer;
import com.example.webplatform.data.entities.dto.users.PostUser;
import com.example.webplatform.data.services.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v0/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public GetUserContainer getUsers(@RequestParam(required = false) University university,
                                     @RequestParam(required = false) Integer course) {
        return userService.getUsers(university, course);
    }

    @GetMapping("/{id}")
    public GetUser findUserById(@PathVariable("id") long id) {
        return userService.findUserById(id);
    }
}
