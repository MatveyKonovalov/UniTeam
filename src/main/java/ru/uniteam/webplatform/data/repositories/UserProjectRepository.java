package ru.uniteam.webplatform.data.repositories;

import ru.uniteam.webplatform.data.entities.UserProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProjectRepository extends JpaRepository<UserProjectEntity, Long> {
}
