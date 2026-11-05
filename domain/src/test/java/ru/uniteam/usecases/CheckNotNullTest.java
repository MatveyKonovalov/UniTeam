package ru.uniteam.usecases;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckNotNullTest {

    @Test
    void checkNotNull_notNull_doesNotThrow() {
        assertDoesNotThrow(() -> CheckNotNull.checkNotNull("value", new RuntimeException("fail")));
        assertDoesNotThrow(() -> CheckNotNull.checkNotNull(42, new IllegalArgumentException("fail")));
    }

    @Test
    void checkNotNull_null_throwsProvidedException() {
        RuntimeException expected = new IllegalArgumentException("custom message");
        RuntimeException actual = assertThrows(IllegalArgumentException.class,
                () -> CheckNotNull.checkNotNull(null, expected));
        assertSame(expected, actual);
        assertEquals("custom message", actual.getMessage());
    }
}
