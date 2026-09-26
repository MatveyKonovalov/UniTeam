package com.example.usecases;

import com.example.models.Skill;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CheckSkills {
    public static void checkSkills(List<Skill> skills, int maxAmount, String message) {
        CheckNotNull.checkNotNull(skills, new IllegalArgumentException(message));

        Set<Skill> skillsSet = new HashSet<>(skills);
        if (skillsSet.size() > maxAmount) {
            throw new IllegalArgumentException(message);
        }
    }
}
