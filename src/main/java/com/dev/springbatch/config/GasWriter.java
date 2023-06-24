package com.dev.springbatch.config;

import com.dev.springbatch.entity.Gas;
import com.dev.springbatch.repository.GasRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;


@Configuration
public class GasWriter implements ItemWriter<Gas> {

    @Autowired
    private GasRepository gasRepository;

    @Override
    public void write(List<? extends Gas> list) throws Exception {
        System.out.println("Thread Name:"+ Thread.currentThread().getName());
        gasRepository.saveAll(list);
    }
}
