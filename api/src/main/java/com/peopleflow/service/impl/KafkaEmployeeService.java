package com.peopleflow.service.impl;

import com.peopleflow.dao.Employee;
import com.peopleflow.dao.EmployeeRepository;
import com.peopleflow.exception.AddEmployeeException;
import com.peopleflow.exception.UpdateEmployeeStatusException;
import com.peopleflow.lib.EmployeeDto;
import com.peopleflow.lib.EmployeeState;
import com.peopleflow.service.EmployeeService;
import com.peopleflow.service.RequestService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@AllArgsConstructor
public class KafkaEmployeeService implements EmployeeService {

    private KafkaTemplate<String, EmployeeDto> kafkaTemplate;

    private RequestService requestService;

    private String employeeTopic;

    private EmployeeRepository employeeRepository;


    @Override
    public EmployeeDto addEmployee(EmployeeDto employee) {
        employee.setId(requestService.getCurrentRequestId());
        sendEmployeeEvent(employee);
        return employee;
    }

    @Override
    public EmployeeState check(String employeeId) {
        return updateState(employeeId, EmployeeState.IN_CHECK);
    }

    @Override
    public EmployeeState approve(String employeeId) {
        return updateState(employeeId, EmployeeState.APPROVED);
    }

    @Override
    public EmployeeState activate(String employeeId) {
        return updateState(employeeId, EmployeeState.ACTIVE);
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeState status(String id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.map(Employee::getState).orElse(null);
    }

    private EmployeeState updateState(String employeeId, EmployeeState state) {
        log.info("Update employee '{}' with status '{}'", employeeId, state);
        EmployeeDto employee = createEmployeeEvent(employeeId, state);
        try {
            sendEmployeeEvent(employee);
        } catch (Exception exc) {
            log.error(exc.getMessage(), exc);
            throw new UpdateEmployeeStatusException(employeeId, state);
        }
        return state;
    }

    private EmployeeDto createEmployeeEvent(String userId, EmployeeState state) {
        EmployeeDto employee = new EmployeeDto();
        employee.setId(userId);
        employee.setState(state);
        return employee;
    }

    private void sendEmployeeEvent(EmployeeDto employee) {
        try {
            kafkaTemplate.send(employeeTopic, employee);
        } catch (Exception exc) {
            log.error(exc.getMessage(), exc);
            throw new AddEmployeeException(exc);
        }
    }
}
