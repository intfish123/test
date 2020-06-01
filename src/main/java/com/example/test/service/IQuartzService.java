package com.example.test.service;

import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.List;
import java.util.Map;

public interface IQuartzService {
    void addJob(Class<? extends QuartzJobBean> jobClass, String jobName, String jobGroupName, int jobTime, int jobTimes, Map<String, Object> jobData);

    void addJob(Class<? extends QuartzJobBean> jobClass, String jobName, String jobGroupName, String jobTime, Map<String, Object> jobData);

    void updateJob(String jobName, String jobGroupName, String jobTime);

    void deleteJob(String jobName, String jobGroupName);

    void pauseJob(String jobName, String jobGroupName);

    void resumeJob(String jobName, String jobGroupName);

    void runAJobNow(String jobName, String jobGroupName);

    List<Map<String, Object>> queryAllJob();

    List<Map<String, Object>> queryRunJob();
}
