package ru.uniteam.datastructure;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PairTest {

    @Test
    void pair_holdsValues() {
        Pair<String, Integer> pair = new Pair<>("hello", 42);
        assertEquals("hello", pair.first());
        assertEquals(42, pair.second());
    }

    @Test
    void pair_nullsAllowed() {
        Pair<String, String> pair = new Pair<>(null, null);
        assertNull(pair.first());
        assertNull(pair.second());
    }

    @Test
    void pair_equalsAndHashCode() {
        Pair<String, Integer> p1 = new Pair<>("a", 1);
        Pair<String, Integer> p2 = new Pair<>("a", 1);
        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
    }
}
