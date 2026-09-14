package com.example.webplatform.data.entities;

import jakarta.persistence.*;

import java.util.Objects;

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

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private ProjectRoleEntity projectRoleEntity;

    public UserProjectEntity() {
    }

    public UserProjectEntity(UserEntity userEntity, ProjectEntity projectEntity, ProjectRoleEntity projectRoleEntity) {
        this.userEntity = userEntity;
        this.projectEntity = projectEntity;
        this.projectRoleEntity = projectRoleEntity;
    }

    public ProjectRoleEntity getProjectRoleEntity() {
        return projectRoleEntity;
    }

    public Long getId() {
        return id;
    }


    public ProjectEntity getProjectEntity() {
        return projectEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setProjectEntity(ProjectEntity projectEntity) {
        this.projectEntity = projectEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public void setProjectRoleEntity(ProjectRoleEntity projectRoleEntity) {
        this.projectRoleEntity = projectRoleEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserProjectEntity that)) return false;
        if (id != null && that.id != null) return id.equals(that.id);
        return Objects.equals(userEntity, that.userEntity)
                && Objects.equals(projectEntity, that.projectEntity);
    }

    @Override
    public int hashCode() {
        if (id != null) return id.hashCode();
        return Objects.hash(
                userEntity != null ? userEntity.getId() : null,
                projectEntity != null ? projectEntity.getProjectId() : null
        );
    }
}
