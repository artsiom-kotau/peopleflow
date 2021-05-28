package com.peopleflow.config;

import com.peopleflow.employee.service.employee.EmployeeEventListener;
import com.peopleflow.employee.service.state.StateProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}
