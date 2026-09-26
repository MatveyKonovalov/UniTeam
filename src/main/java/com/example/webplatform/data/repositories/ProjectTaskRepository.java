package com.example.webplatform.data.repositories;

import com.example.webplatform.data.entities.ProjectTaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectTaskRepository extends JpaRepository<ProjectTaskEntity, Long> {
}
