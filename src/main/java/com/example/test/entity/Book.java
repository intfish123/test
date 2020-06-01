package com.example.test.entity;

import com.example.test.enums.Week;
import lombok.Data;

@Data
public class Book {
    private String id;
    private String name;
    private Week week;
}
