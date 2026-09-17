package com.example.webplatform.data.entities;

import com.example.models.Skill;
import com.example.models.University;
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


    @Column(name="university", nullable = false, length = 100)
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
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

    public void setStars(int stars) {
        this.stars = stars;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public Set<UserProjectEntity> getUsersLinks() {
        return usersLinks;
    }

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

    public ProjectEntity() {
    }

    public ProjectEntity(String name, String description, int stars,
                         University university) {
        this.name = name;
        this.description = description;
        this.stars = stars;
        this.university = university;
    }
}
