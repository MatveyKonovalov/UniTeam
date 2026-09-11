package com.example.models.usersImpl;

import com.example.models.project.Project;
import com.example.models.users.UserProfessionalInfo;

import java.util.Set;

public class UserProfessionalInfoImpl implements UserProfessionalInfo {
    private final Set<Project> projectsWhereEmployee;
    private final Set<Project> projectsWhereCapitan;

    public UserProfessionalInfoImpl(Set<Project> projectsWhereEmployee, Set<Project> projectsWhereCapitan) {
        this.projectsWhereEmployee = projectsWhereEmployee;
        this.projectsWhereCapitan = projectsWhereCapitan;
    }

    @Override
    public Set<Project> getProjectsWhereEmployee() {
        return projectsWhereEmployee;
    }

    @Override
    public void addNewEmployeeProject(Project project) {
        this.projectsWhereEmployee.add(project);
    }

    @Override
    public void addNewCapitanProject(Project project) {
        this.projectsWhereCapitan.add(project);
    }

    @Override
    public void clearAllEmployeeProjects() {
        this.projectsWhereEmployee.clear();
    }

    @Override
    public void clearAllCapitanProject() {
        this.projectsWhereCapitan.clear();
    }

    @Override
    public Set<Project> getProjectsWhereCapitan() {
        return projectsWhereCapitan;
    }

    @Override
    public void removeEmployeeProject(Project project) {
        projectsWhereEmployee.remove(project);
    }

    @Override
    public void removeCapitanProject(Project project) {
        projectsWhereCapitan.remove(project);
    }
}
