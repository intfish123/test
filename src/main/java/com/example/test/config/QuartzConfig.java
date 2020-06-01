package com.example.test.config;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
public class QuartzConfig {
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(){
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setOverwriteExistingJobs(true);
        schedulerFactoryBean.setStartupDelay(60);
        return schedulerFactoryBean;
    }
    @Bean(name = "scheduler")
    public Scheduler scheduler(){
        return schedulerFactoryBean().getScheduler();
    }

    @PostConstruct
    public void startScheduler(){
        try {
            scheduler().start();
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
        }
    }
}
