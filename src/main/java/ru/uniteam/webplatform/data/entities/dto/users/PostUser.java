package ru.uniteam.webplatform.data.entities.dto.users;

import ru.uniteam.models.Skill;
import ru.uniteam.models.University;
import ru.uniteam.models.users.UserData;

import java.util.List;

public class PostUser extends UserData {
    public PostUser(String firstName, String lastName, String email, University university, int course, List<Skill> skills, String password) {
        super(firstName, lastName, email, university, course, skills, password);
    }
}