package ru.uniteam.webplatform.data.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.uniteam.webplatform.data.entities.ProjectEntity;
import ru.uniteam.webplatform.data.entities.ProjectTaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.uniteam.webplatform.data.entities.UserEntity;

import java.util.List;

@Repository
public interface ProjectTaskRepository extends JpaRepository<ProjectTaskEntity, Long> {
    @Modifying
    @Query("DELETE FROM ProjectTaskEntity p WHERE p.userEntity = :user AND p.projectEntity = :project")
    void deleteAllTasksByUserAndProject(UserEntity user, ProjectEntity project);

    @Query("SELECT p FROM ProjectTaskEntity WHERE p.userEntity = :user ORDER BY p.deadLine")
    List<ProjectTaskEntity> getUserTasks(UserEntity user);
}
