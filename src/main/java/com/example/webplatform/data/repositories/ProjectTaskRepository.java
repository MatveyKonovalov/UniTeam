package com.example.webplatform.data.repositories;

import com.example.webplatform.data.entities.ProjectTaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectTaskRepository extends JpaRepository<ProjectTaskEntity, Long> {
}
