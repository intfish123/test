package com.example.test.service.impl;

import com.example.test.entity.Student;
import com.example.test.service.IStudentService;
import com.example.test.util.JacksonUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class StudentServiceImpl implements IStudentService {

    @Override
    public void add(Student param){

        try {
            System.out.println("param:"+ JacksonUtils.obj2json(param));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
