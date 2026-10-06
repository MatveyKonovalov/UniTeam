package ru.uniteam.webplatform.data.entities.dto.projects;

import ru.uniteam.models.Skill;
import ru.uniteam.models.projects.ProjectType;
import ru.uniteam.usecases.CheckCourse;
import ru.uniteam.usecases.CheckNotNull;
import ru.uniteam.usecases.skills.CheckUniversityStack;
import lombok.Getter;

import java.util.List;

@Getter
public class PostProject {
    private static final String incorrectTitleMessage = "The project name must not be empty and must not exceed 100 characters.";
    private static final String incorrectProjectTypeMessage = "The project type must not be null";


    // getters
    private String title;
    private final String description;
    private final ProjectType projectType;
    private final List<Skill> stack;
    private final int minCourse;

    public PostProject(String title, String description, ProjectType projectType,
                       List<Skill> stack, int minCourse) {
        CheckNotNull.checkNotNull(title, new IllegalArgumentException(incorrectTitleMessage));

        if (!CheckUniversityStack.canAddSkills(stack)){
            throw new IllegalArgumentException("Too many university skills in stack");
        }
        CheckNotNull.checkNotNull(projectType, new IllegalArgumentException(incorrectProjectTypeMessage));
        CheckNotNull.checkNotNull(description, new IllegalArgumentException("The description must not be null"));
        CheckCourse.checkCourse(minCourse);

        this.projectType = projectType;
        this.stack = stack;
        this.description = description;
        setTitle(title);
        this.minCourse = minCourse;
    }

    // setters
    private void setTitle(String title) {
        if (title.length() > 100 || title.isBlank()) {
            throw new IllegalArgumentException(incorrectTitleMessage);
        }
        this.title = title;
    }

}
