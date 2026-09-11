package com.example.models.users;

public interface UserCommonInfo {
    void setPhoneNumber(String phoneNumber);
    void setDescription(String description);
    void setUniversity(String university);
    boolean setAge(int age);
    String getFirstName();
    String getLastName();
    String getEmail();
    int getAge();
    String getPhoneNumber();
    String getDescription();
    String getUniversity();
    void setFirstName(String firstName);
    void setLastName(String lastName);
    boolean setEmail(String email);
}
