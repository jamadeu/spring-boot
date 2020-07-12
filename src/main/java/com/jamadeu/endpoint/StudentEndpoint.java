package com.jamadeu.endpoint;

import com.jamadeu.error.CustomErrorType;
import com.jamadeu.model.Student;
import com.jamadeu.repository.IStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Created by Jean Amadeu 12 julho 2020
 */
@RestController
@RequestMapping("students")
public class StudentEndpoint {

    private final IStudentRepository studentRepository;

    @Autowired
    public StudentEndpoint(IStudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public ResponseEntity<?> listAll() {
        return new ResponseEntity<>(studentRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student == null) {
            return new ResponseEntity<>(new CustomErrorType("Student not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Student student) {
        return new ResponseEntity<>(studentRepository.save(student), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        studentRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Student student) {
        studentRepository.save(student);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }
}
