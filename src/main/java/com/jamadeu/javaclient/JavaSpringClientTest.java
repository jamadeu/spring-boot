package com.jamadeu.javaclient;

import com.jamadeu.model.Student;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * @author Jean Amadeu 07/13/2020
 */
public class JavaSpringClientTest {
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplateBuilder()
                .rootUri("http://localhost:8080/v1/protected/students")
                .basicAuthentication("user", "123123")
                .build();
        Student student = restTemplate.getForObject("/{id}", Student.class, 2);
        ResponseEntity<Student> forEntity = restTemplate.getForEntity("/{id}", Student.class, 2);
        System.out.println(student);
        System.out.println(forEntity);
        Student[] students = restTemplate.getForObject("/", Student[].class);
        System.out.println(Arrays.toString(students));
        ResponseEntity<List<Student>> exchange = restTemplate.exchange(
                "/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Student>>() {
                }
        );

        System.out.println(exchange.getBody());

    }
}
