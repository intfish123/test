package com.example.test.controller;


import com.example.test.common.TaskInfo;
import com.example.test.entity.Student;
import com.example.test.entity.useranalysis.UserAnalysisParams;
import com.example.test.entity.useranalysis.UserAnalysisPeriod;
import com.example.test.service.TestService;
import com.example.test.util.SnowFlake;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/api")
public class TestController {

    @Autowired
    private TestService testService;
    @Resource
    private ThreadPoolTaskExecutor exec;

    @PostMapping("/test")
    public TaskInfo test(@RequestBody UserAnalysisParams params) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        UserAnalysisPeriod.Granularity granularity = params.getPeriods().get(0).getGranularity();
        System.out.println(UserAnalysisPeriod.Granularity.MONTH.equals(granularity));
        System.out.println(objectMapper.writeValueAsString(params));
        return null;
    }
}
