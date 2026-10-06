package ru.uniteam.webplatform.data.services.users;

import ru.uniteam.models.Skill;
import ru.uniteam.models.users.UserData;
import ru.uniteam.webplatform.data.entities.UserEntity;
import ru.uniteam.webplatform.data.entities.dto.users.GetUser;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

@Component
public class UserMapper {
    public UserEntity toUserEntityFromPostUser(UserData postUser) {
        Set<Skill> skills = new HashSet<>(postUser.getSkills());
        return new UserEntity(
                postUser.getFirstName(),
                postUser.getLastName(),
                postUser.getEmail(),
                postUser.getUniversity(),
                skills,
                postUser.getCourse()
        );
    }

    public GetUser toGetUserFromUserEntity(UserEntity userEntity) {
        return new GetUser(
                userEntity.getId(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getEmail(),
                userEntity.getUniversity(),
                userEntity.getCourse(),
                new ArrayList<>(userEntity.getSkills()),
                userEntity.getCurrentAmountProject()
        );
    }

    public List<GetUser> toGetUserFromUserEntity(List<UserEntity> userEntity) {
        return new ArrayList<>(userEntity.stream().map(this::toGetUserFromUserEntity).toList());
    }
}
