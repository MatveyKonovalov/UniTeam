package ru.uniteam.models;

import org.junit.jupiter.api.Test;
import ru.uniteam.models.users.UserData;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDataTest {

    static class TestUserData extends UserData {
        public TestUserData(String firstName, String lastName, String email,
                            University university, int course, List<Skill> skills, String password) {
            super(firstName, lastName, email, university, course, skills, password);
        }
    }

    @Test
    void constructor_valid_createsObject() {
        TestUserData user = new TestUserData("Ivan", "Ivanov", "ivan@example.com",
                University.MSU, 3, List.of(Skill.JAVA), "password123");
        assertEquals("Ivan", user.getFirstName());
        assertEquals("Ivanov", user.getLastName());
        assertEquals("ivan@example.com", user.getEmail());
        assertEquals(University.MSU, user.getUniversity());
        assertEquals(3, user.getCourse());
        assertEquals(1, user.getSkills().size());
        assertEquals(Skill.JAVA, user.getSkills().get(0));
        assertEquals("password123", user.getPassword());
    }

    @Test
    void constructor_nullFirstName_throws() {
        assertThrows(IllegalArgumentException.class, () ->
                new TestUserData(null, "Ivanov", "ivan@example.com", University.MSU, 3, List.of(), "pass"));
    }

    @Test
    void constructor_longFirstName_throws() {
        String longName = "a".repeat(51);
        assertThrows(IllegalArgumentException.class, () ->
                new TestUserData(longName, "Ivanov", "ivan@example.com", University.MSU, 3, List.of(), "pass"));
    }

    @Test
    void constructor_nullLastName_throws() {
        assertThrows(IllegalArgumentException.class, () ->
                new TestUserData("Ivan", null, "ivan@example.com", University.MSU, 3, List.of(), "pass"));
    }

    @Test
    void constructor_longLastName_throws() {
        String longName = "b".repeat(51);
        assertThrows(IllegalArgumentException.class, () ->
                new TestUserData("Ivan", longName, "ivan@example.com", University.MSU, 3, List.of(), "pass"));
    }

    @Test
    void constructor_nullUniversity_throws() {
        assertThrows(IllegalArgumentException.class, () ->
                new TestUserData("Ivan", "Ivanov", "ivan@example.com", null, 3, List.of(), "pass"));
    }

    @Test
    void constructor_invalidCourse_throws() {
        assertThrows(IllegalArgumentException.class, () ->
                new TestUserData("Ivan", "Ivanov", "ivan@example.com", University.MSU, 0, List.of(), "pass"));
        assertThrows(IllegalArgumentException.class, () ->
                new TestUserData("Ivan", "Ivanov", "ivan@example.com", University.MSU, 7, List.of(), "pass"));
    }

    @Test
    void constructor_invalidEmail_throws() {
        assertThrows(Exception.class, () ->
                new TestUserData("Ivan", "Ivanov", "bad-email", University.MSU, 3, List.of(), "pass"));
    }

    @Test
    void constructor_nullPassword_throws() {
        assertThrows(IllegalArgumentException.class, () ->
                new TestUserData("Ivan", "Ivanov", "ivan@example.com", University.MSU, 3, List.of(), null));
    }

    @Test
    void constructor_tooManySkills_throws() {
        List<Skill> many = new ArrayList<>();
        many.add(Skill.JAVA);
        many.add(Skill.PYTHON);
        many.add(Skill.KOTLIN);
        many.add(Skill.GROOVY);
        many.add(Skill.GO);
        many.add(Skill.RUST);
        many.add(Skill.C);
        many.add(Skill.CPP);
        many.add(Skill.C_SHARP);
        many.add(Skill.JAVASCRIPT);
        many.add(Skill.TYPESCRIPT);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                new TestUserData("Ivan", "Ivanov", "ivan@example.com", University.MSU, 3, many, "pass"));
        assertTrue(ex.getMessage().contains("Too may user skills"));
    }

    @Test
    void constructor_exactlyTenSkills_ok() {
        List<Skill> ten = List.of(Skill.JAVA, Skill.PYTHON, Skill.KOTLIN, Skill.GROOVY, Skill.GO,
                Skill.RUST, Skill.C, Skill.CPP, Skill.C_SHARP, Skill.JAVASCRIPT);
        assertDoesNotThrow(() ->
                new TestUserData("Ivan", "Ivanov", "ivan@example.com", University.MSU, 3, ten, "pass"));
    }

    @Test
    void getSkills_returnsCopy() {
        TestUserData user = new TestUserData("Ivan", "Ivanov", "ivan@example.com",
                University.MSU, 3, List.of(Skill.JAVA), "pass");
        List<Skill> skills = user.getSkills();
        skills.clear();
        assertEquals(1, user.getSkills().size());
    }

    @Test
    void toString_containsFields() {
        TestUserData user = new TestUserData("Ivan", "Ivanov", "ivan@example.com",
                University.MSU, 3, List.of(Skill.JAVA), "pass");
        String s = user.toString();
        assertTrue(s.contains("Ivan"));
        assertTrue(s.contains("ivan@example.com"));
    }
}
