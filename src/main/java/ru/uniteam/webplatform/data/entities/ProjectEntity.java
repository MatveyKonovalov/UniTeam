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


    @ElementCollection
    @CollectionTable(
            name = "projects_skills",
            joinColumns = @JoinColumn(name = "project_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "skill", nullable = false, length = 50)
    @Setter
    private Set<Skill> skills = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Setter
    @Column(name = "project_type", nullable = false, length = 50)
    private ProjectType projectType;

    @OneToMany(mappedBy = "projectEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<ProjectTaskEntity> tasks = new HashSet<>();

    @Column(name = "min_course")
    private int minCourse;

    @ManyToMany()
    @JoinTable(
            name = "students_stars",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"),
            uniqueConstraints = @UniqueConstraint(name = "uk_user_project_star",
                    columnNames = {"student_id", "project_id"})
    )
    private final Set<UserEntity> usersStars = new HashSet<>();

    public int getStars() {
        return usersStars.size();
    }

    public ProjectEntity() {
    }

    public void addTask(ProjectTaskEntity newTask) {
        tasks.add(newTask);
    }

    public Set<ProjectTaskEntity> getTasks(){
        return new HashSet<>(tasks);
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

    @Override
    public String toString() {
        return "ProjectEntity(" +
                "projectId=" + projectId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", stars=" + stars +
                ", university=" + university +
                ", projectType=" + projectType +
                ", minCourse=" + minCourse +
                ')';
    }
}
