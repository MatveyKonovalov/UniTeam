package ru.uniteam.webplatform.data.services.users;

import ru.uniteam.models.Skill;
import ru.uniteam.models.University;
import ru.uniteam.webplatform.data.entities.UserEntity;
import ru.uniteam.webplatform.data.entities.dto.users.GetUser;
import ru.uniteam.webplatform.data.entities.dto.users.GetUserContainer;
import ru.uniteam.webplatform.data.repositories.UserRepository;
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

    public UserEntity getUserByEmail(String email) {
        return repository.findUserEntitiesByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User with email=" + email + " not found"));
    }

    public GetUser addSkill(UserEntity user, Skill skill){
        if (user.canBorrowSkill()){
            user.addSkill(skill);

            return mapper.toGetUserFromUserEntity(user);
        }

        throw new IllegalArgumentException("Too many skills");
    }

    public GetUser deleteSkill(UserEntity userEntity, Skill skill){
        userEntity.removeSkill(skill);

        return mapper.toGetUserFromUserEntity(userEntity);
    }

    public GetUser setUniversity(UserEntity userEntity, University university){
        userEntity.setUniversity(university);
        return mapper.toGetUserFromUserEntity(userEntity);
    }

    public GetUser setCourse(UserEntity userEntity, int course){
        userEntity.setCourse(course);
        return mapper.toGetUserFromUserEntity(userEntity);
    }
}
