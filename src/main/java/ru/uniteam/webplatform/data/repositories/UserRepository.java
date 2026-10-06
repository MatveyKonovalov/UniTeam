package ru.uniteam.webplatform.data.repositories;

import ru.uniteam.models.University;
import ru.uniteam.webplatform.data.entities.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findByUniversity(University university);

    List<UserEntity> findByCourse(Integer course);

    List<UserEntity> findByUniversityAndCourse(University university, Integer course);

    Optional<UserEntity> findUserEntitiesByEmail(String email);

    Optional<UserEntity> findUserEntityByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE UserEntity u " +
            "SET u.refreshToken= :refreshToken, u.refreshTokenExpire= :refreshTokenExpire " +
            "WHERE u.email= :email ")
    void updateUserEntityRefreshTokenByEmail(
            @Param("refreshToken") String refreshToken,
            @Param("refreshTokenExpire") LocalDateTime refreshTokenExpire,
            @Param("email") String email);
}
