package com.peopleflow.service.impl;

import com.peopleflow.exception.AddEmployeeException;
import com.peopleflow.lib.EmployeeDto;
import com.peopleflow.service.RequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    private KafkaEmployeeService kafkaEmployeeService;

    @BeforeEach
    public void init() {
        kafkaEmployeeService = new KafkaEmployeeService(kafkaTemplate, requestService, KAFKA_TOPIC);
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

}