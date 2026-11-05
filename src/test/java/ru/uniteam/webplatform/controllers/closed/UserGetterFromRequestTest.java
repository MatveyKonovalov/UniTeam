package ru.uniteam.webplatform.controllers.closed;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.uniteam.webplatform.data.entities.UserEntity;
import ru.uniteam.webplatform.data.services.users.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ru.uniteam.webplatform.TestDataFactory.user;

@ExtendWith(MockitoExtension.class)
class UserGetterFromRequestTest {

    @Mock UserService userService;
    @Mock SecurityContext securityContext;
    @Mock Authentication authentication;

    @Test
    void getUserFromRequest() {
        UserGetterFromRequest getter = new UserGetterFromRequest(userService);
        UserEntity u = user(1L, "a@b.com");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("a@b.com");
        SecurityContextHolder.setContext(securityContext);
        when(userService.getUserByEmail("a@b.com")).thenReturn(u);

        assertSame(u, getter.getUserFromRequest());
    }
}
