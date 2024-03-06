package com.iprody.userprofile.userprofileservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * Configuration class for setting up validation in the application context.
 */

@Configuration
public class ValidationConfig {

    /**
     * Bean definition for creating a Validator instance.
     *
     * @return Validator instance configured with LocalValidatorFactoryBean.
     */
    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }
}
