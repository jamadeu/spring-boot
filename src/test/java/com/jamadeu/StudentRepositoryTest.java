package com.jamadeu;

import com.jamadeu.model.Student;
import com.jamadeu.repository.IStudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author Jean Amadeu 07/17/2020
 */

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class StudentRepositoryTest {

    @Autowired
    private IStudentRepository studentRepository;

    @Test
    public void createShouldPersistData() {
        Student student = new Student("student", "student@exemple.com");
        this.studentRepository.save(student);
        assertThat(student.getId()).isNotNull();
        assertThat(student.getEmail()).isEqualTo("student@exemple.com");
        assertThat(student.getName()).isEqualTo("student");
    }

    @Test
    public void deleteShouldRemoveData() {
        Student student = new Student("student", "student@exemple.com");
        this.studentRepository.save(student);
        this.studentRepository.delete(student);
        assertThat(this.studentRepository.findById(student.getId())).isEmpty();
    }
}
