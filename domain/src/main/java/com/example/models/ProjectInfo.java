package com.example.models;

public record ProjectInfo(
        Long project_id,
        String name,
        String description,
        Integer captainId,
        int starts,
        Integer universityId
) {
}