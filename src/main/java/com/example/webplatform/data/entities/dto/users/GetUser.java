package com.example.webplatform.data.entities.dto.users;

import com.example.models.Skill;
import com.example.models.University;

import java.util.List;

public record GetUser(
        Long id,
        String firstName,
        String lastName,
        String email,
        University university,
        int course,
        List<Skill> skills,
        int amountProjects
) {
}
