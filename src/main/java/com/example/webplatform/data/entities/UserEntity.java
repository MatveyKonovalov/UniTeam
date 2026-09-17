package com.example.webplatform.data.entities;

import com.example.models.Skill;
import com.example.models.University;
import com.example.usecases.CheckEmailUseCase;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(name = "uk_user_email", columnNames = "email"))
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
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
    private Set<Skill> skills = new HashSet<>();

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserProjectEntity> userProjectEntities = new HashSet<>();

    @Column(name = "university", nullable = false, length = 100)
    @Enumerated(EnumType.STRING)
    private University university;

    public UserEntity() {
    }

    public UserEntity(String firstName, String lastName, String email, University university) {
        CheckEmailUseCase.checkEmail(email);
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.university = university;
    }

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Set<Skill> getUserSkills() {
        return skills;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setEmail(String email) {
        CheckEmailUseCase.checkEmail(email);
        this.email = email;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University universityEntity) {
        this.university = universityEntity;
    }

    public Set<UserProjectEntity> getUserProjectEntities() {
        return userProjectEntities;
    }

    public void addSkill(Skill skill) {
        this.skills.add(skill);
    }

    public void removeSkill(Skill skillEntity) {
        this.skills.remove(skillEntity);
    }

    public void addUserProjectEntity(UserProjectEntity userProjectEntity) {
        userProjectEntities.add(userProjectEntity);
    }

    public void removeUserProjectEntity(UserProjectEntity userProjectEntity) {
        userProjectEntities.remove(userProjectEntity);
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "email='" + email + '\'' +
                ", id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
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
