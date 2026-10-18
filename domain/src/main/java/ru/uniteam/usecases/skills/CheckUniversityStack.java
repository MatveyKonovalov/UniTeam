package ru.uniteam.usecases.skills;

import ru.uniteam.models.Skill;

import java.util.List;

public class CheckUniversityStack extends CheckSkills{
    private final static int maxAmount = 20;

    public static boolean canAddSkills(List<Skill> skills){
        return canAddSkills(skills, maxAmount, "University stack must be not null");
    }
    public static boolean canAddSkill(int currentSkillLength){
        return canAddSkill(currentSkillLength, maxAmount);
    }
}
