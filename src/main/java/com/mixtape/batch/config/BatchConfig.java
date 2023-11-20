package com.mixtape.batch.config;

import com.mixtape.batch.validators.MyJobParametersValidators;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.CompositeJobParametersValidator;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@EnableBatchProcessing
public class BatchConfig {


    /* Le job default job paramater est une chose qui se fait a chaque fois avec des rêgles de gestion /*
    /* par exemple: valider un nom de fichier pour plus de sécurité  =) */

    @Bean
    public JobParametersValidator defaultJobParamatersValidators() {
        DefaultJobParametersValidator bean = new DefaultJobParametersValidator();
        bean.setRequiredKeys(new String[] {"formateursFile", "formationsFile", "seancesFile"});
        bean.setOptionalKeys(new String[] {"run.id"});

        return bean;
    }


    /* Premier validator un peu custom */
    @Bean
    public JobParametersValidator myJobParametersValidators() {
        return new MyJobParametersValidators();
    }

    /* Il reste a créer un composite jobParamaters qui regroupe tous les validators et puis envoyer celui ci dans le
    * .validator du Job pour tous les envoyer. */
    @Bean
    public JobParametersValidator compositeJobParametersValidator() {
        CompositeJobParametersValidator bean = new CompositeJobParametersValidator();

        bean.setValidators(Arrays.asList(defaultJobParamatersValidators(), myJobParametersValidators()));
        return bean;
    }


    @Bean
    public Job job1(JobBuilderFactory jobBuilderFactory, Step chargementFormateurStep) {
        return jobBuilderFactory.get("Chargement du premier job")
                .start(chargementFormateurStep)
                .validator(compositeJobParametersValidator())
                .incrementer(new RunIdIncrementer())
                .build();
    }

}
