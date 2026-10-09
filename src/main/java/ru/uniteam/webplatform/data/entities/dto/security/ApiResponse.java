package ru.uniteam.webplatform.data.entities.dto.security;

import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class ApiResponse{
    private final String message;
    private final LocalDateTime time = LocalDateTime.now();

    public ApiResponse(String message){
        this.message = message;
    }

}