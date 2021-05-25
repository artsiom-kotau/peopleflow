package com.peopleflow.employee.service.employee;

import com.peopleflow.employee.dao.EmployeeRepository;
import com.peopleflow.employee.entity.Employee;
import com.peopleflow.exception.EmployeeServiceException;
import com.peopleflow.lib.EmployeeDto;
import com.peopleflow.lib.EmployeeState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class EmployeeServiceImplTest {

    @MockBean
    private EmployeeRepository employeeRepository;

    private EmployeeServiceImpl employeeService;

    @BeforeEach
    public  void init() {
        employeeService = new EmployeeServiceImpl();
        employeeService.setEmployeeMapper(EmployeeMapper.INSTANCE);
        employeeService.setEmployeeRepository(employeeRepository);
    }

    @Test
    public void saveEmployeeSuccessfully() {
        String id = UUID.randomUUID().toString();
        EmployeeDto employeeToSave = new EmployeeDto();
        EmployeeDto savedEmployee = employeeService.create(employeeToSave);
        assertEquals(id, savedEmployee.getId());
        assertEquals(EmployeeState.ADDED, savedEmployee.getState());
    }

    @Test
    public void saveEmployeeWithError() {
        when(employeeRepository.save(any(Employee.class))).thenThrow(RuntimeException.class);
        assertThrows(EmployeeServiceException.class, () -> employeeService.create(new EmployeeDto()));

    }

}