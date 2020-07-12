package com.jamadeu.repository;

import com.jamadeu.model.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Jean Amadeu 12 julho 2020
 */
public interface IStudentRepository extends CrudRepository<Student, Long> {
    List<Student> findByName(String name);
}
