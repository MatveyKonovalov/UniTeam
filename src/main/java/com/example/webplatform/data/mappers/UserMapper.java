package com.example.webplatform.data.mappers;

import com.example.models.users.UserCommonInfo;
import com.example.models.users.UserProfessionalInfo;
import com.example.webplatform.data.entities.UserEntity;

public class UserMapper {
    public static UserCommonInfo toUserCommonInfoFromUserEntity(UserEntity userEntity) {
        return new UserCommonInfo(
                userEntity.getId(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getEmail(),
                userEntity.getUniversity()
        );
    }

    public static UserProfessionalInfo toUserProfessionalInfoFromUserEntity(UserEntity userEntity){
        return new UserProfessionalInfo(
                userEntity.getId(),
                userEntity.getUserSkills()
        );
    }
}
