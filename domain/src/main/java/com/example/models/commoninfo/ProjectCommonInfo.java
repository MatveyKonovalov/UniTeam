package com.example.models.commoninfo;

import com.example.models.University;

public record ProjectCommonInfo(
        Long projectIdd,
        String name,
        String description,
        int starts,
        University university
) {
}