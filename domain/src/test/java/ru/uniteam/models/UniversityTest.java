package ru.uniteam.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UniversityTest {

    @Test
    void findById_existing_returnsUniversity() {
        assertEquals(University.MSU, University.findById(1));
        assertEquals(University.ITMO, University.findById(7));
        assertEquals(University.SUSU, University.findById(20));
    }

    @Test
    void findById_unknown_returnsNull() {
        assertNull(University.findById(0));
        assertNull(University.findById(999));
    }

    @Test
    void findByTitle_existing_returnsUniversity() {
        assertEquals(University.MSU, University.findByTitle("Московский государственный университет имени М. В. Ломоносова"));
        assertEquals(University.MSU, University.findByTitle("московский государственный университет имени м. в. ломоносова"));
    }

    @Test
    void findByTitle_unknownOrNull_returnsNull() {
        assertNull(University.findByTitle("Unknown Uni"));
        assertNull(University.findByTitle(null));
        assertNull(University.findByTitle(""));
    }

    @Test
    void getters_work() {
        assertEquals(1, University.MSU.getUniversityId());
        assertTrue(University.MSU.getTitle().contains("Ломоносова"));
    }
}
