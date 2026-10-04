package com.example.webplatform.data.entities;


import com.example.models.users.ProjectRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users_projects",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_user_project",
                columnNames = {"user_id", "project_id"}))
@Getter
public class UserProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @Setter
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    @Setter
    private ProjectEntity projectEntity;


    @Column(name = "role", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    @Setter
    private ProjectRole projectRole;

    public UserProjectEntity() {
    }

    public UserProjectEntity(UserEntity userEntity, ProjectEntity projectEntity, ProjectRole projectRole) {
        this.userEntity = userEntity;
        this.projectEntity = projectEntity;
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
