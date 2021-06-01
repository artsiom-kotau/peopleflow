package com.peopleflow.service.impl;

import com.peopleflow.dao.Employee;
import com.peopleflow.dao.EmployeeRepository;
import com.peopleflow.exception.AddEmployeeException;
import com.peopleflow.exception.UpdateEmployeeStatusException;
import com.peopleflow.lib.EmployeeDto;
import com.peopleflow.lib.EmployeeState;
import com.peopleflow.service.RequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class KafkaEmployeeServiceTest {

    private static final String KAFKA_TOPIC = "topic";

    @MockBean
    private KafkaTemplate<String, EmployeeDto> kafkaTemplate;

    @MockBean
    private RequestService requestService;

    @MockBean
    private EmployeeRepository employeeRepository;

    private KafkaEmployeeService kafkaEmployeeService;

    @BeforeEach
    public void init() {
        kafkaEmployeeService = new KafkaEmployeeService(kafkaTemplate, requestService, KAFKA_TOPIC, employeeRepository);
    }

    @Test
    public void addEmployeeSuccessfully() {
        String newId = "11111";
        EmployeeDto employee = new EmployeeDto();

        when(requestService.getCurrentRequestId()).thenReturn(newId);
        EmployeeDto addedEmployee = kafkaEmployeeService.addEmployee(employee);

        assertEquals(newId, addedEmployee.getId());
        verify(kafkaTemplate, times(1)).send(eq(KAFKA_TOPIC), same(employee));
    }

    @Test
    public void addEmployeeRuntimeError() {
        String newId = "11111";
        EmployeeDto employee = new EmployeeDto();

        when(requestService.getCurrentRequestId()).thenReturn(newId);

        when(kafkaTemplate.send(any(), any())).thenThrow(RuntimeException.class);

        assertThrows(AddEmployeeException.class, () -> kafkaEmployeeService.addEmployee(employee));
    }

    @Test
    public void inCheckEmployeeSuccessfully() {
        String employeeId = "11111";
        EmployeeState state = kafkaEmployeeService.check(employeeId);
        assertEquals(state, EmployeeState.IN_CHECK);
        verify(kafkaTemplate, times(1)).send(eq(KAFKA_TOPIC), any(EmployeeDto.class));
    }

    @Test
    public void inCheckEmployeeRuntimeError() {
        String employeeId = "11111";
        when(kafkaTemplate.send(any(), any())).thenThrow(RuntimeException.class);
        assertThrows(UpdateEmployeeStatusException.class, () -> kafkaEmployeeService.check(employeeId));
    }

    @Test
    public void inApproveEmployeeSuccessfully() {
        String employeeId = "11111";
        EmployeeState state = kafkaEmployeeService.approve(employeeId);
        assertEquals(state, EmployeeState.APPROVED);
        verify(kafkaTemplate, times(1)).send(eq(KAFKA_TOPIC), any(EmployeeDto.class));
    }

    @Test
    public void inApproveEmployeeRuntimeError() {
        String employeeId = "11111";
        when(kafkaTemplate.send(any(), any())).thenThrow(RuntimeException.class);
        assertThrows(UpdateEmployeeStatusException.class, () -> kafkaEmployeeService.approve(employeeId));
    }

    @Test
    public void activateEmployeeSuccessfully() {
        String employeeId = "11111";
        EmployeeState state = kafkaEmployeeService.activate(employeeId);
        assertEquals(state, EmployeeState.ACTIVE);
        verify(kafkaTemplate, times(1)).send(eq(KAFKA_TOPIC), any(EmployeeDto.class));
    }

    @Test
    public void activateEmployeeRuntimeError() {
        String employeeId = "11111";
        when(kafkaTemplate.send(any(), any())).thenThrow(RuntimeException.class);
        assertThrows(UpdateEmployeeStatusException.class, () -> kafkaEmployeeService.activate(employeeId));
    }

    @Test
    public void getStatusEmployeeSuccessfully() {
        String employeeId = "11111";
        Employee employee = new Employee();
        employee.setId(employeeId);
        employee.setState(EmployeeState.ACTIVE);
        when(employeeRepository.findById(eq(employeeId))).thenReturn(Optional.of(employee));
        EmployeeState state = kafkaEmployeeService.status(employeeId);

        assertEquals(state, EmployeeState.ACTIVE);
    }

    @Test
    public void getStatusEmployee_NotFound() {
        String employeeId = "11111";
        when(employeeRepository.findById(eq(employeeId))).thenReturn(Optional.empty());
        assertNull(kafkaEmployeeService.status(employeeId));
    }

}