package com.example.webplatform.data.entities.dto.projects;

import com.example.models.Skill;
import com.example.models.University;
import com.example.models.projects.ProjectType;
import com.example.usecases.CheckCourse;
import com.example.usecases.CheckNotNull;
import com.example.usecases.CheckSkills;
import com.example.usecases.CheckUniversity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PostProject {
    private final int maxAmountSkillsInStack = 20;

    private static final String incorrectTitleMessage = "The project name must not be empty and must not exceed 100 characters.";
    private static final String incorrectProjectTypeMessage = "The project type must not be null";


    private String title;
    private final String description;
    private final University university;
    private final ProjectType projectType;
    private final List<Skill> stack;
    private int minCourse;

    public PostProject(String title, String description, University university, ProjectType projectType, List<Skill> stack, int minCourse) {
        CheckNotNull.checkNotNull(title, new IllegalArgumentException(incorrectTitleMessage));
        CheckUniversity.checkUniversity(university);
        CheckNotNull.checkNotNull(projectType, new IllegalArgumentException(incorrectProjectTypeMessage));
        CheckSkills.checkSkills(stack, maxAmountSkillsInStack,
                "The project stack must not be null and must not have no more than 20 skills");
        CheckNotNull.checkNotNull(description, new IllegalArgumentException("The description must not be null"));
        CheckCourse.checkCourse(minCourse);

        this.projectType = projectType;
        this.stack = stack;
        this.description = description;
        setTitle(title);
        this.university = university;
        this.minCourse = minCourse;
    }

    // setters
    private void setTitle(String title) {
        if (title.length() > 100 || title.isBlank()) {
            throw new IllegalArgumentException(incorrectTitleMessage);
        }
        this.title = title;
    }

    // getters
    public String getTitle() {
        return title;
    }

    public University getUniversity() {
        return university;
    }

    public ProjectType getProjectType() {
        return projectType;
    }

    public List<Skill> getStack() {
        return stack;
    }

    public String getDescription() {
        return description;
    }

    public int getMinCourse() {
        return minCourse;
    }
}
