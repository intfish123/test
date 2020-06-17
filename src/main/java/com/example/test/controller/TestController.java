package com.example.test.controller;


import com.example.test.entity.Student;
import com.example.test.service.IStudentService;
import com.example.test.service.impl.StudentServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@Slf4j
@RestController
@RequestMapping("/api")
public class TestController {


    @Resource
    private ThreadPoolTaskExecutor exec;
    @Autowired
    private StudentServiceImpl studentService;

    @PostMapping("/test")
    public void test(@Valid Student student) throws Exception {
        try {
//            studentService.add(null);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw e;
        }
    }
    @PostMapping("/test2")
    public void test2(@RequestBody @Valid Student student) throws Exception {
        try {
//            studentService.add(null);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw e;
        }
    }
}
