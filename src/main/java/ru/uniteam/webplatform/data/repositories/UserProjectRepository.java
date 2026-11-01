package ru.uniteam.webplatform.data.repositories;

import org.springframework.data.jpa.repository.Query;
import ru.uniteam.webplatform.data.entities.ProjectEntity;
import ru.uniteam.webplatform.data.entities.UserEntity;
import ru.uniteam.webplatform.data.entities.UserProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProjectRepository extends JpaRepository<UserProjectEntity, Long> {
    @Query("SELECT up FROM UserProjectEntity up " +
            "WHERE up.projectEntity = :entity " +
            "ORDER BY up.joiningTime")
    List<UserProjectEntity> findAllByProjectEntityId(ProjectEntity entity);

    @Query("SELECT up FROM UserProjectEntity up " +
            "WHERE up.projectEntity = :entity AND up.projectRole IN ('EMPLOYEE', 'SUBCAPTAIN', 'CAPTAIN')" +
            "ORDER BY up.joiningTime")
    List<UserProjectEntity> findEmployeesByProjectEntityId(ProjectEntity entity);

    @Query("SELECT up FROM UserProjectEntity up " +
            "WHERE up.projectEntity = :project AND up.userEntity = :user")
    Optional<UserProjectEntity> findUserInProject(UserEntity user, ProjectEntity project);
}
