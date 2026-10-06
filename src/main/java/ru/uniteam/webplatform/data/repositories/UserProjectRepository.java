package ru.uniteam.webplatform.data.repositories;

import org.springframework.data.jpa.repository.Query;
import ru.uniteam.webplatform.data.entities.ProjectEntity;
import ru.uniteam.webplatform.data.entities.UserProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserProjectRepository extends JpaRepository<UserProjectEntity, Long> {
    @Query("SELECT up FROM UserProjectEntity up " +
            "WHERE up.projectEntity = :entity " +
            "ORDER BY up.joiningTime")
    List<UserProjectEntity> findAllByProjectEntityId(ProjectEntity entity);

}
