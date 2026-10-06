package ru.uniteam.webplatform.data.entities.dto.users;

import ru.uniteam.models.Skill;
import ru.uniteam.models.University;

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
