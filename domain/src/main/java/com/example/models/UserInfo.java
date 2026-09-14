package com.example.models;

import java.util.List;

public record UserInfo(
        Integer userId,
        String firstName,
        String lastName,
        String email,
        University university,
        List<Integer> skillsId,
        List<Integer> capitanProjectsId,
        List<Integer> employeeProjectsId){}
