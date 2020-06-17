package com.example.test.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class Student {
    @NotNull
    private Integer id;
    @NotBlank
    private String name;
}
