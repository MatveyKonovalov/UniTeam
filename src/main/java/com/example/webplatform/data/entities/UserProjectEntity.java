package com.example.webplatform.data.entities;

import com.example.models.projects.ProjectRole;
import jakarta.persistence.*;

@Entity
@Table(name = "users_projects",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_user_project",
                columnNames = {"user_id", "project_id"}))
public class UserProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectEntity projectEntity;


    @Column(name = "role", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private ProjectRole projectRole;

    public UserProjectEntity() {
    }

    public UserProjectEntity(UserEntity userEntity, ProjectEntity projectEntity, ProjectRole projectRole) {
        this.userEntity = userEntity;
        this.projectEntity = projectEntity;
        this.projectRole = projectRole;
    }

    public ProjectRole getProjectRole() {
        return projectRole;
    }

    // getters
    public Long getId() {
        return id;
    }

    public ProjectEntity getProjectEntity() {
        return projectEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    // setters
    public void setProjectEntity(ProjectEntity projectEntity) {
        this.projectEntity = projectEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public void setProjectRoleEntity(ProjectRole projectRole) {
        this.projectRole = projectRole;
    }


    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserProjectEntity that)) return false;
        return id != null && id.equals(that.id);
    }
}
