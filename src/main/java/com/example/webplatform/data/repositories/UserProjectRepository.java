package com.example.webplatform.data.repositories;

import com.example.webplatform.data.entities.UserProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProjectRepository extends JpaRepository<UserProjectEntity, Long> {
}
