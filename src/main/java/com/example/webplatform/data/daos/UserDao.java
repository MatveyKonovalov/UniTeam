package com.example.webplatform.data.daos;

import com.example.webplatform.data.entities.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserDao {
    @PersistenceContext
    private EntityManager entityManager;

    public void addNewUser(UserEntity userEntity) {
        entityManager.persist(userEntity);
    }

    public void removeUser(UserEntity userEntity) {
        entityManager.remove(userEntity);
    }

    public void updateUser(UserEntity userEntity) {
        entityManager.merge(userEntity);
    }

    public UserEntity searchUserById(int userId) {
        return entityManager.find(UserEntity.class, userId);
    }

    public Optional<UserEntity> searchUserByEmail(String email) {
        Query query = entityManager.createQuery("FROM UserEntity WHERE email = :email");
        query.setParameter("email", email);
        return query.getResultStream().findFirst();
    }
}
