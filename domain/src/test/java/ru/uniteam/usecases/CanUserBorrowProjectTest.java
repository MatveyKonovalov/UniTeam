package ru.uniteam.usecases;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CanUserBorrowProjectTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4})
    void canBorrow_lessThan5_returnsTrue(int amount) {
        assertTrue(CanUserBorrowProject.canBorrow(amount));
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 6, 10})
    void canBorrow_5orMore_returnsFalse(int amount) {
        assertFalse(CanUserBorrowProject.canBorrow(amount));
    }
}
