package com.jamadeu;

import com.jamadeu.model.Student;
import com.jamadeu.repository.IStudentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @BeforeEach
    public void setup() {
        Student student = new Student(1L, "student", "student@email.com");
        BDDMockito.when(this.studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
    }

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
        ResponseEntity<Student> response = this.restTemplate.getForEntity("/v1/protected/students/{id}", Student.class, 1L);
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    public void getStudentsByIdWhenUserNameAndPasswordAreCorrectShouldReturnStatusCode404() {
        ResponseEntity<Student> response = this.restTemplate.getForEntity("/v1/protected/students/{id}", Student.class, -1);
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    public void deleteWhenUserHasRoleAdminAndStudentExistsShouldReturnStatusCode200() {
        BDDMockito.doNothing().when(this.studentRepository).deleteById(1L);
        ResponseEntity<String> response = this.restTemplate.exchange("/v1/admin/students/{id}", DELETE, null, String.class, 1L);
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    @WithMockUser(username = "admin", password = "123123", roles = {"USER", "ADMIN"})
    public void deleteWhenUserHasRoleAdminAndStudentDoesNotExistsShouldReturnStatusCode404() throws Exception {
        BDDMockito.doNothing().when(this.studentRepository).deleteById(1L);
//        ResponseEntity<String> response = this.restTemplate.exchange("/v1/admin/students/{id}", DELETE, null, String.class, -1L);
//        assertEquals(404, response.getStatusCode().value());
        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/v1/admin/students/{id}", -1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", password = "123123", roles = {"USER"})
    public void deleteWhenUserDoesNotHasRoleAdminShouldReturnStatusCode403() throws Exception {
        BDDMockito.doNothing().when(this.studentRepository).deleteById(1L);
//        this.restTemplate = this.restTemplate.withBasicAuth("user", "123123");
//        ResponseEntity<String> response = this.restTemplate.exchange("/v1/admin/students/{id}", DELETE, null, String.class, -1L);
//        assertEquals(403, response.getStatusCode().value());
        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/v1/admin/students/{id}", -1L))
                .andExpect(status().isForbidden());
    }

    @Test
    public void createWhenNameIsNullShouldReturnStatusCode400BadRequest() throws Exception {
        Student student = new Student(3L, null, "student3@email.com");
        BDDMockito.when(this.studentRepository.save(student)).thenReturn(student);
        ResponseEntity<String> response = this.restTemplate.postForEntity("/v1/admin/students/", student, String.class);
        Assertions.assertEquals(400, response.getStatusCodeValue());
        Assertions.assertTrue(response.getBody().contains("\"fieldMessage\":\"O campo nome é obrigatório\""));
    }

    @Test
    public void createShouldPersistDataAndReturnStatusCode200() throws Exception {
        Student student = new Student(3L, "Student3", "student3@email.com");
        BDDMockito.when(this.studentRepository.save(student)).thenReturn(student);
        ResponseEntity<Student> response = this.restTemplate.postForEntity("/v1/admin/students/", student, Student.class);
        Assertions.assertEquals(200, response.getStatusCodeValue());
        Assertions.assertNotNull(response.getBody().getId());
        Assertions.assertEquals("Student3", response.getBody().getName());
        Assertions.assertEquals("student3@email.com", response.getBody().getEmail());
    }

    @TestConfiguration
    static class Config {
        @Bean
        public static RestTemplateBuilder restTemplateBuilder() {
            return new RestTemplateBuilder().basicAuthentication("admin", "123123");
        }
    }


}
