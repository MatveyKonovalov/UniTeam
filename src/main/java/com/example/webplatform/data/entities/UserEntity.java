package com.example.webplatform.data.entities;

import com.example.models.Skill;
import com.example.models.University;
import com.example.models.exceptions.UserHasTooManyProjectsException;
import com.example.models.security.Role;
import com.example.usecases.CheckCourse;
import com.example.usecases.CheckEmailUseCase;
import com.example.usecases.CheckNotNull;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(name = "uk_user_email", columnNames = "email"))
@Getter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "first_name", nullable = false, length = 50)
    @Setter
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    @Setter
    private String lastName;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @ElementCollection
    @CollectionTable(
            name = "users_skills",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "skill", nullable = false)
    private Set<Skill> skills;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<UserProjectEntity> userProjectEntities = new HashSet<>();

    @Column(name = "university", nullable = false, length = 100)
    @Setter
    @Enumerated(EnumType.STRING)
    private University university;

    @Column(name = "current_amount_project", nullable = false)
    private int currentAmountProject;

    @Column(name = "course", nullable = false)
    private int course;

    @Column(name = "password_hash", nullable = false)
    @Setter
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "user_role_in_system", length = 15)
    private Role role = Role.USER;

    private boolean enable = true;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name="refresh_token_expire")
    private LocalDateTime refreshTokenExpire;

    public UserEntity() {
    }

    public UserEntity(String firstName, String lastName, String email, University university, Set<Skill> skills, int course) {
        CheckNotNull.checkNotNull(skills, new IllegalArgumentException("Skills must be not null"));
        CheckEmailUseCase.checkEmail(email);
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.university = university;
        this.currentAmountProject = 0;
        this.skills = skills;
        this.course = course;
    }

    // custom setters

    public void setEmail(String email) {
        CheckEmailUseCase.checkEmail(email);
        this.email = email;
    }



    private void setCourse(int course) {
        CheckCourse.checkCourse(course);
        this.course = course;
    }

    // management functions
    public void addSkill(Skill skill) {
        this.skills.add(skill);
    }

    public void removeSkill(Skill skillEntity) {
        this.skills.remove(skillEntity);
    }

    public void addUserProjectEntity(UserProjectEntity userProjectEntity) {
        if (currentAmountProject == 5) {
            throw new UserHasTooManyProjectsException(id);
        }
        currentAmountProject += 1;
        userProjectEntities.add(userProjectEntity);
    }

    public void removeUserProjectEntity(UserProjectEntity userProjectEntity) {
        userProjectEntities.remove(userProjectEntity);
        currentAmountProject -= 1;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "email='" + email + '\'' +
                ", id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", currentAmountProject=" + currentAmountProject + '\'' +
                ", course=" + course + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity that)) return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

}
