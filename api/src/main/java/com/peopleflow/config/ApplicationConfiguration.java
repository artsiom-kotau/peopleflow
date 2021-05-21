package com.peopleflow.config;

import com.peopleflow.lib.EmployeeState;
import com.peopleflow.model.request.Employee;
import com.peopleflow.service.RequestService;
import com.peopleflow.service.EmployeeService;
import com.peopleflow.service.impl.IdGenerator;
import com.peopleflow.service.impl.ThreadLocalRequestService;
import com.peopleflow.service.impl.UUIDGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {


    @Bean
    public IdGenerator idGenerator() {
        return new UUIDGenerator();
    }

    @Bean
    public RequestService requestService(IdGenerator idGenerator) {
        return new ThreadLocalRequestService(idGenerator);
    }

    @Bean
    public EmployeeService employeeService() {
        return new EmployeeService() {
            @Override
            public Employee addEmployee(Employee employee) {
                return null;
            }

            @Override
            public EmployeeState check(String userId) {
                return null;
            }

            @Override
            public EmployeeState approve(String userId) {
                return null;
            }

            @Override
            public EmployeeState activate(String userId) {
                return null;
            }

            @Override
            public EmployeeState status(String id) {
                return null;
            }
        };
    }
}
