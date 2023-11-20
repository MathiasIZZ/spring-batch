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
@StepScope
public class ChargementFormateursStepConfig {

    @Bean
    public FlatFileItemReader<Formateur> formateurItemReader(@Value("#{jobParameters['formateursFile']}") final Resource input) {
        return new FlatFileItemReaderBuilder<Formateur>()
                .name("FormateurtItemReader")
                .resource(input)
                .delimited()
                .delimiter(";")
                .names(new String[] {"id", "nom", "prenom", "adresseMail"})
                .targetType(Formateur.class)
                .build();
    }

    @Bean
    public ItemWriter<Formateur> formateurItemWriter() {
        return (items) -> items.forEach(System.out::println);
    }

    @Bean
    public Step chargementFormateurStep(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("chargement des formateurs")
                .<Formateur, Formateur>chunk(10)
                .reader(formateurItemReader(null))
                .writer(formateurItemWriter())
                .build();
    }






}
