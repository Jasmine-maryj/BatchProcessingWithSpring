package com.dev.springbatch.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/jobs")
public class GasJobController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @PostMapping("/importGases")
    public void csvToDb(){
        JobParameters jobParameter = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis()).toJobParameters();

        try {
            jobLauncher.run(job, jobParameter);
        } catch (JobExecutionAlreadyRunningException | JobParametersInvalidException |
                 JobInstanceAlreadyCompleteException | JobRestartException e) {
            throw new RuntimeException(e);
        }
    }
}
