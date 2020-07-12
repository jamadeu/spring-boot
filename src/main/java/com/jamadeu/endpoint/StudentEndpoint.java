package com.jamadeu.endpoint;

import com.jamadeu.error.ResourceNotFoundException;
import com.jamadeu.model.Student;
import com.jamadeu.repository.IStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<?> listAll(Pageable pageable) {
        return new ResponseEntity<>(studentRepository.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        checkStudentExists(id);
        Optional<Student> student = studentRepository.findById(id);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @GetMapping(path = "/findByName/{name}")
    public ResponseEntity<?> findByName(@PathVariable String name) {
        return new ResponseEntity<>(studentRepository.findByNameIgnoreCaseContaining(name), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody Student student) {
        return new ResponseEntity<>(studentRepository.save(student), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        checkStudentExists(id);
        studentRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Student student) {
        checkStudentExists(student.getId());
        studentRepository.save(student);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    private void checkStudentExists(Long id) {
        if (studentRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("Student not found for ID: " + id);
        }

    }
}
