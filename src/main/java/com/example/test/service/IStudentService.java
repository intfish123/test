package com.example.test.service;

import com.example.test.entity.Student;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
public interface IStudentService {

    void add(@NotNull Student student);
}
