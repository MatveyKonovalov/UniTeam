package ru.uniteam.webplatform.data.repositories;

import ru.uniteam.models.University;
import ru.uniteam.webplatform.data.entities.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
    @Query("SELECT p FROM ProjectEntity p " +
            "WHERE p.university = :university AND p.minCourse <= :minCourse")
    List<ProjectEntity> findProjectsByUniversityAndMinCourse(University university, int minCourse);

    List<ProjectEntity> findProjectEntitiesByUniversity(University university);

    @Query("SELECT p FROM ProjectEntity p " +
            "WHERE p.minCourse <= :minCourse")
    List<ProjectEntity> findProjectEntitiesByMinCourse(int minCourse);
}
