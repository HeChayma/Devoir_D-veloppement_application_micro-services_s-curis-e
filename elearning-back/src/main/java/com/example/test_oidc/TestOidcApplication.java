package com.example.test_oidc;

import com.example.test_oidc.entites.Courses;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class TestOidcApplication {

    // Liste partagée en mémoire (pour le TP)
    public static List<Courses> COURSES = new ArrayList<>();

	public static void main(String[] args) {
		SpringApplication.run(TestOidcApplication.class, args);
	}
    @Bean
    CommandLineRunner initCourses() {

        return args -> {
            COURSES.add(new Courses(1L, "Math", "Prof1"));
            COURSES.add(new Courses(2L, "Physics", "Prof2"));
            COURSES.add(new Courses(3L, "Biology", "Prof3"));

        };
    }

}
