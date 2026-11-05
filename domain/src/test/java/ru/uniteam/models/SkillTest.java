package ru.uniteam.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SkillTest {

    @Test
    void searchSkillById_existing_returnsSkill() {
        assertEquals(Skill.JAVA, Skill.searchSkillById(Skill.JAVA.getId()));
        assertEquals(Skill.PYTHON, Skill.searchSkillById(Skill.PYTHON.getId()));
    }

    @Test
    void searchSkillById_unknown_returnsNull() {
        assertNull(Skill.searchSkillById(-1));
        assertNull(Skill.searchSkillById(99999));
    }

    @Test
    void skillWithThisIdExists() {
        assertTrue(Skill.skillWithThisIdExists(Skill.JAVA.getId()));
        assertFalse(Skill.skillWithThisIdExists(-100));
    }

    @Test
    void getSkillByTitle_existing_caseInsensitive() {
        assertEquals(Skill.JAVA, Skill.getSkillByTitle("Java"));
        assertEquals(Skill.JAVA, Skill.getSkillByTitle("java"));
        assertEquals(Skill.JAVA, Skill.getSkillByTitle("JAVA"));
    }

    @Test
    void getSkillByTitle_unknownOrNull_returnsNull() {
        assertNull(Skill.getSkillByTitle(null));
        assertNull(Skill.getSkillByTitle("NotASkill"));
        assertNull(Skill.getSkillByTitle(""));
    }

    @Test
    void skillWithThisTitleExists() {
        assertTrue(Skill.skillWithThisTitleExists("Java"));
        assertFalse(Skill.skillWithThisTitleExists("Unknown"));
    }

    @Test
    void getters() {
        assertNotNull(Skill.JAVA.getTitle());
        assertTrue(Skill.JAVA.getId() > 0);
    }
}
