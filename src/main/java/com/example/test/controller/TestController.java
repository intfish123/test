package com.example.test.controller;


import com.example.test.common.TaskInfo;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api")
public class TestController {


    @Resource
    private ThreadPoolTaskExecutor exec;

    @PostMapping("/test")
    public TaskInfo test(@RequestBody String params) throws Exception {
        return null;
    }
}
