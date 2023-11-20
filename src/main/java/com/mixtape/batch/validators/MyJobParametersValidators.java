package com.mixtape.batch.validators;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.util.StringUtils;

public class MyJobParametersValidators implements JobParametersValidator {


    @Override
    public void validate(JobParameters jobParameters) throws JobParametersInvalidException {
        if (!StringUtils.endsWithIgnoreCase(jobParameters.getString("formateursFile"), "csv")) {
            throw new JobParametersInvalidException("Le fichier des formateurs doit être au format .csv");
        }
        if (!StringUtils.endsWithIgnoreCase(jobParameters.getString("formationsFile"), "xml")) {
            throw new JobParametersInvalidException("Le fichier doit être de format .xml");
        }
        if (StringUtils.endsWithIgnoreCase(jobParameters.getString("seancesFile"), "xml")) {
            throw new JobParametersInvalidException("Le fichier doit être au format .xml");
        }
    }
}
