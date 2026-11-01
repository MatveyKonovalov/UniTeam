package ru.uniteam.webplatform.data.repositories;

import org.jspecify.annotations.NonNull;
import ru.uniteam.models.University;
import ru.uniteam.webplatform.data.entities.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
    @Query("SELECT p FROM ProjectEntity p " +
            "WHERE p.university = :university AND p.minCourse <= :minCourse " +
            "ORDER BY p.stars DESC")
    List<ProjectEntity> findProjectsByUniversityAndMinCourse(University university, int minCourse);

    @Query("SELECT p FROM ProjectEntity p ORDER BY p.stars DESC")
    List<ProjectEntity> findProjectEntitiesByUniversity(University university);

    @Query("SELECT p FROM ProjectEntity p " +
            "WHERE p.minCourse <= :minCourse " +
            "ORDER BY p.stars DESC")
    List<ProjectEntity> findProjectEntitiesByMinCourse(int minCourse);

    @Override
    @Query("SELECT p FROM ProjectEntity p ORDER BY p.stars DESC")
    @NonNull
    List<ProjectEntity> findAll();
}
