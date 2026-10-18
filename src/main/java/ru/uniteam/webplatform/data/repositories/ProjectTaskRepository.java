package ru.uniteam.webplatform.data.repositories;

import ru.uniteam.webplatform.data.entities.ProjectTaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectTaskRepository extends JpaRepository<ProjectTaskEntity, Long> {
}
