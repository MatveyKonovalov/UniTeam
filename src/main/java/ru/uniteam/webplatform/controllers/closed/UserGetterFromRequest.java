package ru.uniteam.webplatform.controllers.closed;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.uniteam.webplatform.data.entities.UserEntity;
import ru.uniteam.webplatform.data.services.users.UserService;

@Component
@RequiredArgsConstructor
public class UserGetterFromRequest {

    protected final UserService userService;

    public UserEntity getUserFromRequest() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.getUserByEmail(email);
    }
}
