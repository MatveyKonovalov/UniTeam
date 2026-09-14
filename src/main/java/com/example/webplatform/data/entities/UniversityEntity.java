package com.example.webplatform.data.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "universities")
public class UniversityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "university_id")
    private Long universityId;

    @Column(name = "name", nullable = false, unique = true, length = 150)
    private String name;

    @OneToMany(mappedBy = "universityEntity")
    private Set<UserEntity> userEntitySet = new HashSet<>();

    @OneToMany(mappedBy = "universityEntity")
    private Set<ProjectEntity> projectEntities = new HashSet<>();


    public UniversityEntity() {}

    public UniversityEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Long getUniversityId() {
        return universityId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ProjectEntity> getProjectEntities() {
        return projectEntities;
    }

    public Set<UserEntity> getUserEntitySet() {
        return userEntitySet;
    }

    public void addUserEntity(UserEntity userEntity){
        this.userEntitySet.add(userEntity);
    }
    public void removeUserEntity(UserEntity userEntity){
        this.userEntitySet.remove(userEntity);
    }
    public void addProjectEntity(ProjectEntity projectEntity){
        this.projectEntities.add(projectEntity);
    }
    public void removeProjectEntity(ProjectEntity projectEntity){
        this.projectEntities.remove(projectEntity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UniversityEntity that)) return false;
        return name != null && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
