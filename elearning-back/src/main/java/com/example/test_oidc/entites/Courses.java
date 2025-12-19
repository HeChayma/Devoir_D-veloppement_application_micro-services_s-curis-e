package com.example.test_oidc.entites;


public class Courses {

    private Long id;
    private String name;
    private String Teacher;

    public Courses() {}

    public Courses(Long id, String name, String T) {
        this.id = id;
        this.name = name;
        this.Teacher = T;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeacher() {
        return Teacher;
    }

    public void setTeacher(String T) {
        this.Teacher = T;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

