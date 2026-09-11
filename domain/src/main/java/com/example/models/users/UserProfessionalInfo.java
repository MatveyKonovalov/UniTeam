package com.example.models.users;



import com.example.models.project.Project;

import java.util.Set;

public interface UserProfessionalInfo {
    Set<Project> getProjectsWhereEmployee();
    void addNewEmployeeProject(Project project);
    void addNewCapitanProject(Project project);
    void clearAllEmployeeProjects();
    void clearAllCapitanProject();
    Set<Project> getProjectsWhereCapitan();
    void removeEmployeeProject(Project project);
    void removeCapitanProject(Project project);
}