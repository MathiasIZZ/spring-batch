package com.mixtape.batch.config;

import com.mixtape.batch.models.Formateur;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class ChargementAssuresStepConfig {



    @StepScope
    @Bean
    public FlatFileItemReader<Formateur> assuresItemReader(@Value("#{jobParameters['formateursFile']}") final Resource input) {
        return new FlatFileItemReaderBuilder<Formateur>()
                .name("assuresItemReader")
                .resource(input)
                .delimited()
                .delimiter(";")
                .names(new String[] {"id", "nom", "prenom", "adresseMail"})
                .targetType(Formateur.class)
                .build();
    }


    @Bean
    public ItemWriter<Formateur> assuresItemWriter() {

        return (items) -> items.forEach(System.out::println);
    }



    public Step chargementAssuresStep(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("chargement des assurés")
                .<Formateur, Formateur>chunk(10)
                .reader(assuresItemReader(null))
                .writer(assuresItemWriter())
                .build();
    }


}
