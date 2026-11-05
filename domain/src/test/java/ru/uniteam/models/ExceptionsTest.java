package ru.uniteam.models;

import org.junit.jupiter.api.Test;
import ru.uniteam.models.exceptions.*;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionsTest {

    @Test
    void incorrectUserEmailException_message() {
        assertEquals("Incorrect user email.", new IncorrectUserEmailException().getMessage());
    }

    @Test
    void permissionException_defaultAndCustom() {
        assertEquals("Not enough rights", new PermissionException().getMessage());
        assertEquals("custom", new PermissionException("custom").getMessage());
    }

    @Test
    void userHasTooManyProjectsException_message() {
        assertTrue(new UserHasTooManyProjectsException(42L).getMessage().contains("42"));
    }

    @Test
    void otherExceptions_haveMessages() {
        assertNotNull(new IncorrectDescriptionException().getMessage());
        assertNotNull(new IncorrectProjectRoleException().getMessage());
        assertNotNull(new IncorrectTaskTypeException().getMessage());
        assertNotNull(new IncorrectTaskUndefinedException().getMessage());
        assertNotNull(new IncorrectTitleException().getMessage());
    }
}
