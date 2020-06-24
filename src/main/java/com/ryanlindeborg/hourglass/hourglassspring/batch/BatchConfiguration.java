package com.ryanlindeborg.hourglass.hourglassspring.batch;

import com.ryanlindeborg.hourglass.hourglassspring.model.security.RevokedToken;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.util.Date;
import java.util.HashMap;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Bean
    JpaPagingItemReader<RevokedToken> revokedTokenItemReader() {
        String jpqlQuery = "SELECT t FROM RevokedToken t WHERE t.expirationDate < :currentDate";
        HashMap<String, Object> queryParameters = new HashMap<>();
        queryParameters.put("currentDate", new Date());

        JpaPagingItemReader<RevokedToken> revokedTokenItemReader = new JpaPagingItemReader<RevokedToken>();
        revokedTokenItemReader.setEntityManagerFactory(entityManagerFactory);
        revokedTokenItemReader.setQueryString(jpqlQuery);
        revokedTokenItemReader.setParameterValues(queryParameters);

        return revokedTokenItemReader;
    }

    @Bean
    public RevokedTokenProcessor processor() {
        return new RevokedTokenProcessor();
    }

    @Bean
    public org.springframework.batch.core.Job importRevokedTokenJob() {
        return jobBuilderFactory.get("importRevokedTokenJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<RevokedToken, RevokedToken> chunk(20)
                .reader(revokedTokenItemReader())
                .processor(processor())
                .build();
    }

}
