package com.example.models.usersImpl;

import com.example.models.users.User;
import com.example.models.users.UserCommonInfo;
import com.example.models.users.UserProfessionalInfo;

public class UserImpl implements User {
    private final UserCommonInfo userCommonIfo;
    private final UserProfessionalInfo userProfessionalInfo;
    private final Integer id;

    public UserImpl(Integer id, UserCommonInfo userCommonIfo, UserProfessionalInfo userProfessionalInfo) {
        this.userCommonIfo = userCommonIfo;
        this.userProfessionalInfo = userProfessionalInfo;
        this.id = id;
    }

    @Override
    public UserProfessionalInfo getUserProfessionalInfo() {
        return userProfessionalInfo;
    }

    @Override
    public UserCommonInfo getUserCommonInfo() {
        return userCommonIfo;
    }

    @Override
    public Integer getId(){
        return id;
    }
}
