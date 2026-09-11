package com.example.models;


import com.example.models.project.Project;
import com.example.models.users.User;

import java.util.Set;

public class ProjectImpl implements Project {
    private final Integer id;
    private final Set<Skill> usedSkillStack;
    private User capitan;
    private final Set<User> employees;
    private String projectDescription;


    public ProjectImpl(Integer id, Set<Skill> usedSkillStack, User capitan,
                       Set<User> employees, String projectDescription) {
        setCapitan(capitan);
        setProjectDescription(projectDescription);
        this.id = id;
        this.usedSkillStack = usedSkillStack;
        this.employees = employees;
    }

    public ProjectImpl(Set<Skill> usedSkillStack, User capitan,
                       Set<User> employees, String projectDescription) {
        this(null, usedSkillStack, capitan, employees, projectDescription);
    }

    @Override
    public void addSkillInStack(Skill skill) {
        usedSkillStack.add(skill);
    }

    @Override
    public void deleteSkillInStack(Skill skill) {
        usedSkillStack.remove(skill);
    }

    @Override
    public void clearStack() {
        usedSkillStack.clear();
    }

    @Override
    public Set<Skill> getUsedSkillStack() {
        return usedSkillStack;
    }

    @Override
    public void addNewEmployee(User employee) {
        employee.getUserProfessionalInfo().addNewEmployeeProject(this);
        employees.add(employee);
    }

    @Override
    public void removeEmployee(User employee) {
        employee.getUserProfessionalInfo().removeEmployeeProject(this);
        employees.remove(employee);
    }

    @Override
    public void clearEmployees() {
        for (var employee : employees) {
            employee.getUserProfessionalInfo().removeEmployeeProject(this);
        }
        employees.clear();
    }

    @Override
    public Set<User> getEmployees() {
        return employees;
    }

    @Override
    public User getCapitan() {
        return capitan;
    }

    @Override
    public void setCapitan(User capitan) {
        this.capitan.getUserProfessionalInfo().removeCapitanProject(this);
        capitan.getUserProfessionalInfo().addNewCapitanProject(this);
        this.capitan = capitan;
    }

    @Override
    public String getProjectDescription() {
        return projectDescription;
    }

    @Override
    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    @Override
    public Integer getId() {
        return id;
    }
}