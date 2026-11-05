package ru.uniteam.usecases;

import org.junit.jupiter.api.Test;
import ru.uniteam.models.University;

import static org.junit.jupiter.api.Assertions.*;

class CheckUniversityTest {

    @Test
    void checkUniversity_valid_doesNotThrow() {
        assertDoesNotThrow(() -> CheckUniversity.checkUniversity(University.MSU));
        assertDoesNotThrow(() -> CheckUniversity.checkUniversity(University.ITMO));
    }

    @Test
    void checkUniversity_null_throws() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> CheckUniversity.checkUniversity(null));
        assertEquals("The university must not be null", ex.getMessage());
    }
}
