package com.example.test.service;

import com.example.test.entity.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestService {
    public List<Student> queryList(List<Student> student) throws InterruptedException {
        List<Student> resultList = new ArrayList<>();
        Thread.sleep(10*1000);
        resultList.add(new Student("1","name1"));
        resultList.add(new Student("2","name2"));
        resultList.add(new Student("3","name3"));
        return resultList;
    }
}
