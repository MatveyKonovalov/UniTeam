package ru.uniteam.usecases;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CheckCourseTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6})
    void checkCourse_valid_doesNotThrow(int course) {
        assertDoesNotThrow(() -> CheckCourse.checkCourse(course));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, 7, 10, 100})
    void checkCourse_invalid_throws(int course) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> CheckCourse.checkCourse(course));
        assertTrue(ex.getMessage().contains("1 to 6"));
    }
}
