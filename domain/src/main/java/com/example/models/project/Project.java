package com.example.models.project;

import com.example.models.Skill;
import com.example.models.users.User;

import java.util.Set;

public interface Project {
    void addSkillInStack(Skill skill);

    void deleteSkillInStack(Skill skill);

    void clearStack();

    Set<Skill> getUsedSkillStack();

    void addNewEmployee(User employee);

    void removeEmployee(User employee);

    void clearEmployees();

    Set<User> getEmployees();

    User getCapitan();

    void setCapitan(User capitan);

    String getProjectDescription();

    void setProjectDescription(String projectDescription);

    Integer getId();
}
