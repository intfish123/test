package com.example.test.entity;

import lombok.Data;

@Data
public class Student {
    public Student(String id,String name){
        this.id = id;
        this.name = name;
    }
    public Student(){}
    private String id;
    private String name;
}
