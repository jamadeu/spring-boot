package com.jamadeu.javaclient;

import com.jamadeu.model.Student;

/**
 * @author Jean Amadeu 07/13/2020
 */
public class JavaSpringClientTest {


    public static void main(String[] args) {

        Student studentPost = new Student();
        studentPost.setName("John Doe");
        studentPost.setEmail("john.doe@exemple.com");

        JavaClientDAO dao = new JavaClientDAO();
        System.out.println(dao.findByID(5));
        System.out.println(dao.listAll());
        System.out.println(dao.save(studentPost));

    }
}
