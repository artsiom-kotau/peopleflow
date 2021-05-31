package com.peopleflow.employee.config;

import com.peopleflow.lib.EmployeeDto;
import com.peopleflow.service.EmployeeService;
import com.peopleflow.service.RequestService;
import com.peopleflow.service.impl.IdGenerator;
import com.peopleflow.service.impl.KafkaEmployeeService;
import com.peopleflow.service.impl.ThreadLocalRequestService;
import com.peopleflow.service.impl.UUIDGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class ApplicationConfiguration {

    @Value(value = "${kafka.employee.topic}")
    private String employeeTopic;


    @Bean
    public IdGenerator idGenerator() {
        return new UUIDGenerator();
    }

    @Bean
    public RequestService requestService(IdGenerator idGenerator) {
        return new ThreadLocalRequestService(idGenerator);
    }

    @Bean
    public EmployeeService employeeService(KafkaTemplate<String, EmployeeDto> employeeKafkaTemplate, RequestService requestService) {
        return new KafkaEmployeeService(employeeKafkaTemplate, requestService, employeeTopic);
    }
}
