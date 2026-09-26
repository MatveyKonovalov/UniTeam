package com.example.webplatform.data.entities.dto.users;

import com.example.models.Skill;
import com.example.models.University;
import com.example.usecases.*;

import java.util.List;

public class PostUser {
    private final String incorrectFirstNameMessage = "The first name must not be null and length must be < 50";
    private final String incorrectLastNameMessage = "The last name must not be null and length must be < 50";


    private String firstName;
    private String lastName;
    private String email;
    private final University university;
    private final int course;
    private final List<Skill> skills;

    public PostUser(String firstName, String lastName, String email, University university, int course, List<Skill> skills) {
        CheckUniversity.checkUniversity(university);
        CheckSkills.checkSkills(skills, 10, "");
        CheckNotNull.checkNotNull(firstName, new IllegalArgumentException(incorrectFirstNameMessage));
        CheckNotNull.checkNotNull(lastName, new IllegalArgumentException(incorrectLastNameMessage));
        CheckCourse.checkCourse(course);

        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);

        this.university = university;
        this.course = course;
        this.skills = skills;
    }

    private void setFirstName(String firstName){
        CheckNotNull.checkNotNull(firstName, new IllegalArgumentException(incorrectFirstNameMessage));
        if (firstName.length() > 50) throw new IllegalArgumentException(incorrectFirstNameMessage);
        this.firstName = firstName;

    }

    private void setLastName(String lastName){
        CheckNotNull.checkNotNull(lastName, new IllegalArgumentException(incorrectLastNameMessage));
        if (lastName.length() > 50) throw new IllegalArgumentException(incorrectLastNameMessage);
        this.lastName = lastName;

    }
    private void setEmail(String email){
        CheckEmailUseCase.checkEmail(email);
        this.email = email;
    }

    // getters
    public University getUniversity() {
        return university;
    }

    public int getCourse() {
        return course;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
}
