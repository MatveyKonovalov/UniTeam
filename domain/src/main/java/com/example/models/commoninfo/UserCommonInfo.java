package com.example.models.commoninfo;

import com.example.models.University;

public record UserCommonInfo(
        Long userId,
        String firstName,
        String lastName,
        String email,
        University university) { }
