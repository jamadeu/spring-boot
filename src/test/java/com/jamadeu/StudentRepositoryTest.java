package com.jamadeu;

import com.jamadeu.model.Student;
import com.jamadeu.repository.IStudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author Jean Amadeu 07/17/2020
 */

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class StudentRepositoryTest {
    @Autowired
    private IStudentRepository studentRepository;

    private Validator validator;

    @BeforeEach
    public void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

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

    @Test
    public void updateShouldUpdateAndPersistData() {
        Student student = new Student("student", "student@exemple.com");
        this.studentRepository.save(student);
        student.setName("student2");
        student.setEmail("student2@exemple.com");
        this.studentRepository.save(student);
        Student updatedStudent = this.studentRepository.findById(student.getId()).orElse(null);
        assertThat(updatedStudent.getName()).isEqualTo("student2");
        assertThat(updatedStudent.getEmail()).isEqualTo("student2@exemple.com");
    }

    @Test
    public void findByNameIgnoreCaseContainingShouldIgnoreCase() {
        Student student = new Student("student", "student@exemple.com");
        Student student2 = new Student("Student", "student2@exemple.com");
        this.studentRepository.save(student);
        this.studentRepository.save(student2);
        List<Student> list = this.studentRepository.findByNameIgnoreCaseContaining("student");
        assertThat(list.size()).isEqualTo(2);
    }

    @Test
    public void createWhenNameIsNullShouldThrowConstraintViolationException() {
        Student student = new Student("", "email@gmail.com");
        Set<ConstraintViolation<Student>> violations = this.validator.validate(student);
        ConstraintViolation<Student> violation = violations.iterator().next();
        assertThat(violations.size()).isEqualTo(1);
        assertThat(violation.getMessageTemplate()).isEqualTo("O campo nome é obrigatório");
    }

    @Test
    public void createWhenEmailIsNullShouldThrowConstraintViolationException() {
        Student student = new Student("student", "");
        Set<ConstraintViolation<Student>> violations = this.validator.validate(student);
        ConstraintViolation<Student> violation = violations.iterator().next();
        assertThat(violations.size()).isEqualTo(1);
        assertThat(violation.getMessageTemplate()).isEqualTo("O campo email é obrigatório");
    }

    @Test
    public void createWhenEmailIsNotValidShouldThrowConstraintViolationException() {
        Student student = new Student("student", "asd");
        Set<ConstraintViolation<Student>> violations = this.validator.validate(student);
        ConstraintViolation<Student> violation = violations.iterator().next();
        System.out.println(violation);
        assertThat(violations.size()).isEqualTo(1);
        assertThat(violation.getMessageTemplate()).isEqualTo("{javax.validation.constraints.Email.message}");

    }
}
