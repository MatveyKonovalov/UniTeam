package com.example.webplatform.data.services.users;

import com.example.models.University;
import com.example.webplatform.data.entities.UserEntity;
import com.example.webplatform.data.entities.dto.users.GetUser;
import com.example.webplatform.data.entities.dto.users.GetUserContainer;
import com.example.webplatform.data.entities.dto.users.PostUser;
import com.example.webplatform.data.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper mapper) {
        this.repository = userRepository;
        this.mapper = mapper;
    }

    public GetUserContainer getUsers(University university, Integer course) {
        if (university != null && course != null) {
            return new GetUserContainer
                    (mapper.toGetUserFromUserEntity(repository.findByUniversityAndCourse(university, course)));
        } else if (university != null) {
            return new GetUserContainer(mapper.toGetUserFromUserEntity(repository.findByUniversity(university)));
        } else if (course != null) {
            return new GetUserContainer(mapper.toGetUserFromUserEntity(repository.findByCourse(course)));
        } else {
            return new GetUserContainer(mapper.toGetUserFromUserEntity(repository.findAll()));
        }
    }

    public GetUser findUserById(long id) {
        Optional<UserEntity> user = repository.findById(id);

        if (user.isEmpty()) {
            throw new IllegalArgumentException("User with id=" + id + " not found");
        }
        return mapper.toGetUserFromUserEntity(user.get());
    }

    public void deleteUserById(long id) {
        repository.deleteById(id);
    }
}
