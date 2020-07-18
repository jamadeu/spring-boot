package com.jamadeu;

import com.jamadeu.model.Student;
import com.jamadeu.repository.IStudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Jean Amadeu 07/18/2020
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class StudentEndpointTest {
    @Autowired
    private TestRestTemplate restTemplate;
    @LocalServerPort
    private int port;

    @MockBean
    private IStudentRepository studentRepository;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void listStudentsWhenUserNameAndPasswordAreIncorrectShouldReturnStatusCode401() {
        this.restTemplate = this.restTemplate.withBasicAuth("invalid username", "invalid password");
        ResponseEntity<String> response = this.restTemplate.getForEntity("/v1/protected/students", String.class);
        assertEquals(401, response.getStatusCode().value());
    }

    @Test
    public void getStudentsByIdWhenUserNameAndPasswordAreIncorrectShouldReturnStatusCode401() {
        this.restTemplate = this.restTemplate.withBasicAuth("invalid username", "invalid password");
        ResponseEntity<String> response = this.restTemplate.getForEntity("/v1/protected/students/1", String.class);
        assertEquals(401, response.getStatusCode().value());
    }

    @Test
    public void listStudentsWhenUserNameAndPasswordAreCorrectShouldReturnStatusCode200() {
        List<Student> students = asList(new Student(1L, "student", "student@email.com"),
                new Student(2L, "student2", "student2@email.com"));
        BDDMockito.when(this.studentRepository.findAll()).thenReturn(students);
        ResponseEntity<String> response = this.restTemplate.getForEntity("/v1/protected/students/", String.class);
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    public void getStudentsByIdWhenUserNameAndPasswordAreCorrectShouldReturnStatusCode200() {
        Student student = new Student(1L, "student", "student@email.com");
        BDDMockito.when(this.studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
        ResponseEntity<Student> response = this.restTemplate.getForEntity("/v1/protected/students/{id}", Student.class, student.getId());
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    public void getStudentsByIdWhenUserNameAndPasswordAreCorrectShouldReturnStatusCode404() {
        ResponseEntity<Student> response = this.restTemplate.getForEntity("/v1/protected/students/{id}", Student.class, -1);
        assertEquals(404, response.getStatusCode().value());
    }

    @TestConfiguration
    static class Config {
        @Bean
        public static RestTemplateBuilder restTemplateBuilder() {
            return new RestTemplateBuilder().basicAuthentication("admin", "123123");
        }
    }


}
