package com.jamadeu.repository;

import com.jamadeu.model.Student;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * Created by Jean Amadeu 12 julho 2020
 */
@Validated
public interface IStudentRepository extends PagingAndSortingRepository<Student, Long> {
    List<Student> findByNameIgnoreCaseContaining(String name);
}
