package com.example.webplatform.data.services.users;

import com.example.models.Skill;
import com.example.webplatform.data.entities.UserEntity;
import com.example.webplatform.data.entities.dto.users.GetUser;
import com.example.webplatform.data.entities.dto.users.PostUser;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

@Component
public class UserMapper {
    public UserEntity toUserEntityFromPostUser(PostUser postUser) {
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
                new ArrayList<>(userEntity.getUserSkills()),
                userEntity.getCurrentAmountProject()
        );
    }

    public List<GetUser> toGetUserFromUserEntity(List<UserEntity> userEntity) {
        return new ArrayList<>(userEntity.stream().map(this::toGetUserFromUserEntity).toList());
    }
}
