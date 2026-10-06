package com.example.usecases.skills;

import com.example.models.Skill;

import java.util.List;

public class CheckUserSkills extends CheckSkills {
    private static final int maxAmount = 10;
    public static boolean canAddSkills(List<Skill> skills){
        return canAddSkills(skills, maxAmount, "Skills must not be null");
    }

    public static boolean canAddSkill(int currentSkillLength){
        return canAddSkill(currentSkillLength, maxAmount);
    }
}
