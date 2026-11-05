package ru.uniteam.usecases.skills;

import org.junit.jupiter.api.Test;
import ru.uniteam.models.Skill;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CheckUserSkillsTest {

    @Test
    void canAddSkills_null_throws() {
        assertThrows(IllegalArgumentException.class, () -> CheckUserSkills.canAddSkills(null));
    }

    @Test
    void canAddSkills_empty_ok() {
        assertTrue(CheckUserSkills.canAddSkills(List.of()));
    }

    @Test
    void canAddSkills_upTo10_ok() {
        List<Skill> skills = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            skills.add(Skill.values()[i]);
        }
        assertTrue(CheckUserSkills.canAddSkills(skills));
    }

    @Test
    void canAddSkills_moreThan10_false() {
        List<Skill> skills = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            skills.add(Skill.values()[i]);
        }
        assertFalse(CheckUserSkills.canAddSkills(skills));
    }

    @Test
    void canAddSkills_duplicatesCountAsOne() {
        List<Skill> skills = List.of(Skill.JAVA, Skill.JAVA, Skill.JAVA);
        assertTrue(CheckUserSkills.canAddSkills(skills));
    }

    @Test
    void canAddSkill_currentLessThan10_true() {
        assertTrue(CheckUserSkills.canAddSkill(9));
        assertFalse(CheckUserSkills.canAddSkill(10));
    }
}
