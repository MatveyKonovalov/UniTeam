package com.example.webplatform.data.repositories;

import com.example.models.University;
import com.example.webplatform.data.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findByUniversity(University university);
    List<UserEntity> findByCourse(Integer course);
    List<UserEntity> findByUniversityAndCourse(University university, Integer course);
}
