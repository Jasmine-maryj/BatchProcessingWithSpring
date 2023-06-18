package com.dev.springbatch.config;

import com.dev.springbatch.entity.Gas;
import com.dev.springbatch.repository.GasRepository;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class SpringBatchConfig {

    private JobBuilderFactory jobBuilderFactory;

    private StepBuilderFactory stepBuilderFactory;

    private GasRepository gasRepository;

    @Bean
    public FlatFileItemReader<Gas> reader(){
        FlatFileItemReader<Gas> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new FileSystemResource("src/main/resources/demo.csv"));
        flatFileItemReader.setName("CSVReader");
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setLineMapper(lineMapper());
        return flatFileItemReader;
    }

    private LineMapper<Gas> lineMapper() {   //how to read the csv file and how to map the field values to the Gas variables
        DefaultLineMapper<Gas> lineMapper = new DefaultLineMapper<>();  //extract

        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setDelimiter(",");
        delimitedLineTokenizer.setStrict(false);
        delimitedLineTokenizer.setNames("id", "PM1", "PM2", "NO2", "NH3", "CO", "SO2", "O3", "PH", "Status");

        BeanWrapperFieldSetMapper<Gas> fieldSetMapper = new BeanWrapperFieldSetMapper<>();  //mapping
        fieldSetMapper.setTargetType(Gas.class);

        lineMapper.setLineTokenizer(delimitedLineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

    //processor
    @Bean
    public GasProcessor gasProcessor(){
        return new GasProcessor();
    }

    //writer
    @Bean
    public RepositoryItemWriter<Gas> repositoryItemWriter(){
        RepositoryItemWriter<Gas> writer = new RepositoryItemWriter<>();
        writer.setRepository(gasRepository);
        writer.setMethodName("save");
        return writer;
    }

    //create a step and give all the components(w, p, r) to it.
    @Bean
    public Step step1(){
        return stepBuilderFactory.get("csv-step")
                .<Gas, Gas>chunk(10)
                .reader(reader())
                .processor(gasProcessor())
                .writer(repositoryItemWriter())
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public Job job(){
        return jobBuilderFactory.get("importGases")
                .flow(step1())
                .end().build();
    }

    @Bean
    public TaskExecutor taskExecutor(){
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setConcurrencyLimit(10);
        return asyncTaskExecutor;
    }

}
