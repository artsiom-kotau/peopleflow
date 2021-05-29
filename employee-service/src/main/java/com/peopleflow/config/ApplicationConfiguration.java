package com.peopleflow.config;

import com.peopleflow.employee.service.employee.EmployeeEventListener;
import com.peopleflow.employee.service.state.StateProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public StateProcessor stateProcessor() {
        return dto -> {

        };
    }

    @Bean
    public EmployeeEventListener employeeEventListener(StateProcessor stateProcessor) {
        return new EmployeeEventListener(stateProcessor);
    }

    @Bean
    public RecordMessageConverter messageConverter() {  return new StringJsonMessageConverter();  }
}
