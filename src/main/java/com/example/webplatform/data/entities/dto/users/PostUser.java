package com.example.webplatform.data.entities.dto.users;

import com.example.models.Skill;
import com.example.models.University;
import com.example.models.users.UserData;
import com.example.usecases.*;
import com.example.usecases.skills.CheckSkills;
import com.example.usecases.skills.CheckUserSkills;

import java.util.List;

public class PostUser extends UserData {
    public PostUser(String firstName, String lastName, String email, University university, int course, List<Skill> skills, String password) {
        super(firstName, lastName, email, university, course, skills, password);
    }
}