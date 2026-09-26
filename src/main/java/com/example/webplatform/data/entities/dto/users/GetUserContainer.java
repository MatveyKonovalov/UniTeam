package com.example.webplatform.data.entities.dto.users;

import java.util.List;

public record GetUserContainer(
        List<GetUser> users
) {
}
