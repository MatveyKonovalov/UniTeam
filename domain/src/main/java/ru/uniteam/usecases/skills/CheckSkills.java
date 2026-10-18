package ru.uniteam.usecases.skills;

import ru.uniteam.models.Skill;
import ru.uniteam.usecases.CheckNotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CheckSkills {
    protected static boolean canAddSkills(List<Skill> skills, int maxAmount, String message) {
        CheckNotNull.checkNotNull(skills, new IllegalArgumentException(message));

        Set<Skill> skillsSet = new HashSet<>(skills);
        return skillsSet.size() <= maxAmount;
    }

    protected static boolean canAddSkill(int currentSkillLength, int maxAmount){
        return currentSkillLength < maxAmount;
    }
}
