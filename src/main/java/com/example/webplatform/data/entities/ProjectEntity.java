package com.example.webplatform.data.entities;

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

    @ManyToOne
    @JoinColumn(name = "university_id", nullable = false)
    private UniversityEntity universityEntity;

    @OneToMany(mappedBy = "projectEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserProjectEntity> usersLinks = new HashSet<>();


    @ManyToMany
    @JoinTable(name = "projects_skills",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private Set<SkillEntity> skillEntities = new HashSet<>();

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

    public UniversityEntity getUniversityEntity() {
        return universityEntity;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public Set<SkillEntity> getSkillEntities() {
        return skillEntities;
    }

    public Set<UserProjectEntity> getUsersLinks() {
        return usersLinks;
    }

    public void setSkillEntities(Set<SkillEntity> skillEntities) {
        this.skillEntities = skillEntities;
    }

    public void addSkillInStack(SkillEntity skillEntity) {
        this.skillEntities.add(skillEntity);
    }

    public void removeSkillInStack(SkillEntity skillEntity) {
        this.skillEntities.remove(skillEntity);
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
                         UniversityEntity universityEntity){
        this.name = name;
        this.description = description;
        this.stars = stars;
        this.universityEntity = universityEntity;
    }
}
