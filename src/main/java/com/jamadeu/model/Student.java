package com.jamadeu.model;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * Created by Jean Amadeu 12 julho 2020
 */
@Entity
public class Student extends AbstractEntity {
    @NotEmpty
    private String name;
    @NotEmpty
    @Email
    private String email;

    public Student() {
    }

    public Student(@NotEmpty String name, @NotEmpty @Email String email) {
        this.name = name;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
