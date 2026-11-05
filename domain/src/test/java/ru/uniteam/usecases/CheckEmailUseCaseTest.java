package ru.uniteam.usecases;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.uniteam.models.exceptions.IncorrectUserEmailException;

import static org.junit.jupiter.api.Assertions.*;

class CheckEmailUseCaseTest {

    @Test
    void checkEmail_validEmail_doesNotThrow() {
        assertDoesNotThrow(() -> CheckEmailUseCase.checkEmail("user@example.com"));
        assertDoesNotThrow(() -> CheckEmailUseCase.checkEmail("user.name+tag@domain.co.uk"));
        assertDoesNotThrow(() -> CheckEmailUseCase.checkEmail("a@b.cd"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "invalid", "no@at", "@nodomain.com", "user@", "user@domain", "user@.com"})
    void checkEmail_invalidEmail_throws(String email) {
        assertThrows(IncorrectUserEmailException.class, () -> CheckEmailUseCase.checkEmail(email));
    }

    @Test
    void checkEmail_null_throws() {
        assertThrows(IncorrectUserEmailException.class, () -> CheckEmailUseCase.checkEmail(null));
    }
}
