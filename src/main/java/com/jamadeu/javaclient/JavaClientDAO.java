package com.jamadeu.javaclient;

import com.jamadeu.model.PagebleResponse;
import com.jamadeu.model.Student;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author Jean Amadeu 07/17/2020
 */
public class JavaClientDAO {
    private RestTemplate restTemplate = new RestTemplateBuilder()
            .rootUri("http://localhost:8080/v1/protected/students")
            .basicAuthentication("user", "123123")
            .build();
    private RestTemplate restTemplateAdmin = new RestTemplateBuilder()
            .rootUri("http://localhost:8080/v1/admin/students")
            .basicAuthentication("admin", "123123")
            .build();

    private static HttpHeaders createJSONHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    public Student findByID(long id) {
        return restTemplate.getForObject("/{id}", Student.class, id);
    }

    public List<Student> listAll() {
        ResponseEntity<PagebleResponse<Student>> exchange = restTemplate.exchange(
                "/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PagebleResponse<Student>>() {
                }
        );

        return exchange.getBody().getContent();
    }

    public Student save(Student student) {
        ResponseEntity<Student> exchangePost = restTemplateAdmin.exchange(
                "/",
                HttpMethod.POST, new HttpEntity<>(student, createJSONHeader()),
                Student.class
        );

        return exchangePost.getBody();
    }

}
