package ru.uniteam.webplatform.data.entities;

import ru.uniteam.models.Skill;
import ru.uniteam.models.University;
import ru.uniteam.models.projects.ProjectType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "projects")
@Getter
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "name", nullable = false, length = 100)
    @Setter
    private String name;

    @Column(name = "description")
    @Setter
    private String description;

    @Column(name = "stars")
    @Setter
    private int stars;

    @Column(name = "university", nullable = false, length = 100)
    @Enumerated(EnumType.STRING)
    private University university;

    @OneToMany(mappedBy = "projectEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserProjectEntity> usersLinks = new HashSet<>();

    @ElementCollection
    @CollectionTable(
            name = "projects_skills",
            joinColumns = @JoinColumn(name = "project_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "skill", nullable = false, length = 50)
    private Set<Skill> skills = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Setter
    @Column(name = "project_type", nullable = false, length = 50)
    private ProjectType projectType;

    @OneToMany(mappedBy = "projectEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProjectTaskEntity> projectTaskEntities = new HashSet<>();

    @Column(name = "min_course")
    private int minCourse;

    public ProjectEntity() {
    }

    public ProjectEntity(String name, String description, int stars,
                         University university, ProjectType projectType, int minCourse) {
        this.name = name;
        this.description = description;
        this.stars = stars;
        this.university = university;
        this.projectType = projectType;
        this.minCourse = minCourse;
    }


    // manager functions
    public void addSkillInStack(Skill skill) {
        this.skills.add(skill);
    }

    public void removeSkillInStack(Skill skill) {
        this.skills.remove(skill);
    }

    public void addUserInProject(UserProjectEntity projectEntity) {
        this.usersLinks.add(projectEntity);
    }

    public void removeUserInProject(UserProjectEntity projectEntity) {
        this.usersLinks.remove(projectEntity);
    }

    public void addProjectTaskEntity(ProjectTaskEntity projectTaskEntity) {
        this.projectTaskEntities.add(projectTaskEntity);
    }

    public void removeProjectTaskEntity(ProjectTaskEntity projectTaskEntity){
        this.projectTaskEntities.remove(projectTaskEntity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProjectEntity that)) return false;
        return projectId != null && projectId.equals(that.projectId);
    }

    @Override
    public int hashCode() {
        return projectId != null ? projectId.hashCode() : 0;
    }
}
