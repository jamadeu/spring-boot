package com.jamadeu.model;

import javax.persistence.Entity;

/**
 * Created by Jean Amadeu 12 julho 2020
 */
@Entity
public class Student extends AbstractEntity {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
