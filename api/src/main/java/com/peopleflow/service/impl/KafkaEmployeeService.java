package com.peopleflow.service.impl;

import com.peopleflow.exception.AddEmployeeException;
import com.peopleflow.lib.EmployeeState;
import com.peopleflow.model.request.Employee;
import com.peopleflow.service.EmployeeService;
import com.peopleflow.service.RequestService;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

@Slf4j
@AllArgsConstructor
public class KafkaEmployeeService implements EmployeeService {

    private KafkaTemplate<String, Employee> kafkaTemplate;

    private RequestService requestService;

    private String employeeTopic;


    @Override
    public Employee addEmployee(Employee employee) {
        employee.setId(requestService.getCurrentRequestId());
        sendEmployeeEvent(employee);
        return employee;
    }

    @Override
    public EmployeeState updateState(String userId, EmployeeState state) {
        Employee employee = createEmployeeEvent(userId, state);
        sendEmployeeEvent(employee);
        return state;
    }

    private Employee createEmployeeEvent(String userId, EmployeeState state) {
        Employee employee = new Employee();
        employee.setId(userId);
        employee.setState(state);
        return employee;
    }

    private void sendEmployeeEvent(Employee employee) {
        try {
            kafkaTemplate.send(employeeTopic, employee);
        } catch (Exception exc) {
            log.error(exc.getMessage(), exc);
            throw new AddEmployeeException(exc);
        }

    }
}
