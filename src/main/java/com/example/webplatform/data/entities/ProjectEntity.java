package com.example.webplatform.data.entities;

import com.example.models.Skill;
import com.example.models.University;
import com.example.models.projects.ProjectType;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "projects")
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "stars")
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
    @Column(name = "project_type", nullable = false, length = 50)
    private ProjectType projectType;

    @OneToMany(mappedBy = "projectEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProjectTaskEntity> projectTaskEntities = new HashSet<>();

    public ProjectEntity() {
    }

    public ProjectEntity(String name, String description, int stars,
                         University university, ProjectType projectType) {
        this.name = name;
        this.description = description;
        this.stars = stars;
        this.university = university;
        this.projectType = projectType;
    }

    // getters
    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public int getStars() {
        return stars;
    }

    public Long getProjectId() {
        return projectId;
    }

    public University getUniversity() {
        return university;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public Set<UserProjectEntity> getUsersLinks() {
        return usersLinks;
    }

    public ProjectType getProjectType() {
        return projectType;
    }

    // setters
    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public void setProjectType(ProjectType projectType) {
        this.projectType = projectType;
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
