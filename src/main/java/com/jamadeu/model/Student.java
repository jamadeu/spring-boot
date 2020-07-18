package com.jamadeu.model;


import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * Created by Jean Amadeu 12 julho 2020
 */
@Entity
public class Student extends AbstractEntity {
    @NotEmpty(message = "O campo nome é obrigatório")
    private String name;

    @NotEmpty(message = "O campo email é obrigatório")
    @Email
    private String email;

    public Student() {
    }

    public Student(@NotEmpty String name, @NotEmpty @Email String email) {
        this.name = name;
        this.email = email;
    }

    public Student(Long id, @NotEmpty String name, @NotEmpty @Email String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + this.name + '\'' +
                ", email='" + this.email + '\'' +
                '}';
    }
}
