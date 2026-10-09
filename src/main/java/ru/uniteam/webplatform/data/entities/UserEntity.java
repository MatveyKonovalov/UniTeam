package ru.uniteam.webplatform.data.entities;

import ru.uniteam.models.Skill;
import ru.uniteam.models.University;
import ru.uniteam.models.exceptions.UserHasTooManyProjectsException;
import ru.uniteam.models.security.Role;
import ru.uniteam.models.users.ProjectRole;
import ru.uniteam.usecases.CanUserBorrowProject;
import ru.uniteam.usecases.CheckCourse;
import ru.uniteam.usecases.CheckEmailUseCase;
import ru.uniteam.usecases.CheckNotNull;
import ru.uniteam.usecases.skills.CheckUserSkills;
import jakarta.persistence.*;
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
    private int currentAmountProject = 0;

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
    @Setter
    private String refreshToken;

    @Column(name = "refresh_token_expire")
    @Setter
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

    public void setCourse(int course) {
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

    public void addProjectEntity(ProjectEntity projectEntity, ProjectRole role) {
        // Если этот проект уже есть у пользователя
        if (userProjectEntities.stream().map(UserProjectEntity::getProjectEntity).toList().contains(projectEntity)) {
            return;
        }

        if (!canBorrowNewProject()) {
            throw new UserHasTooManyProjectsException(id);
        }
        currentAmountProject += 1;
        userProjectEntities.add(new UserProjectEntity(this, projectEntity, role));
    }



    public void projectCancel(UserProjectEntity userProjectEntity) {
        userProjectEntity.setProjectRole(ProjectRole.CANCELLED);
        currentAmountProject--;
    }
    public void leaveTheProject(UserProjectEntity up){
        userProjectEntities.remove(up);
        currentAmountProject --;
    }

    public boolean canBorrowNewProject() {
        return CanUserBorrowProject.canBorrow(currentAmountProject);
    }

    public boolean canBorrowSkill() {
        return CheckUserSkills.canAddSkill(skills.size());
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
