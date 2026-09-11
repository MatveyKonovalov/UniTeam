package com.example.models.usersImpl;

import com.example.models.exceptions.IncorrectUserAgeException;
import com.example.models.exceptions.IncorrectUserEmailException;
import com.example.models.users.UserCommonInfo;

import java.util.regex.Pattern;

public class UserCommonInfoImpl implements UserCommonInfo {
    private String firstName;
    private String lastName;
    private String email;
    private int age;
    private String phoneNumber;
    private String description;
    private String university;

    private static final Pattern EMAIL_PATTERN = Pattern.compile(".*@.*");

    private static String checkEmail(String email) {
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IncorrectUserEmailException();
        }
        return email;
    }

    private static int checkAge(int age) {
        if (age < 0 || age > 120) {
            throw new IncorrectUserAgeException();
        }
        return age;
    }


    public UserCommonInfoImpl(String firstName, String lastName, String email, int age, String phoneNumber,
                              String description, String university) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = checkEmail(email);
        this.age = checkAge(age);
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.university = university;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getUniversity() {
        return university;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    @Override
    public boolean setEmail(String email) {
        try {
            this.email = checkEmail(email);
            return true;
        } catch (IncorrectUserEmailException e) {
            return false;
        }
    }

    @Override
    public boolean setAge(int age) {
        try {
            this.age = checkAge(age);
            return true;
        } catch (IncorrectUserAgeException e) {
            return false;
        }
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void setUniversity(String university) {
        this.university = university;
    }
}