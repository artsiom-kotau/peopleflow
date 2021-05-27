package com.peopleflow.employee.service.employee;

import com.peopleflow.employee.dao.EmployeeRepository;
import com.peopleflow.employee.entity.Employee;
import com.peopleflow.exception.EmployeeServiceException;
import com.peopleflow.lib.EmployeeDto;
import com.peopleflow.lib.EmployeeState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
        employeeToSave.setId(id);
        Employee employeeEntity = new Employee();
        employeeEntity.setId(id);
        employeeEntity.setState(EmployeeState.ADDED);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employeeEntity);
        EmployeeDto savedEmployee = employeeService.create(employeeToSave);
        assertEquals(id, savedEmployee.getId());
        assertEquals(EmployeeState.ADDED, savedEmployee.getState());
    }

    @Test
    public void saveEmployeeWithError() {
        when(employeeRepository.save(any(Employee.class))).thenThrow(RuntimeException.class);
        assertThrows(EmployeeServiceException.class, () -> employeeService.create(new EmployeeDto()));

    }

    @Test
    public void updateEmployeeSuccessfully() {
        String id = UUID.randomUUID().toString();
        EmployeeState state = EmployeeState.IN_CHECK;
        EmployeeDto employeeToSave = new EmployeeDto();
        employeeToSave.setId(id);
        employeeToSave.setState(state);
        Employee employeeEntity = new Employee();
        employeeEntity.setId(id);
        when(employeeRepository.findById(eq(id))).thenReturn(Optional.of(employeeEntity));
        when(employeeRepository.save(any(Employee.class))).thenAnswer((Answer<Employee>) invocationOnMock -> invocationOnMock.getArgument(0));
        EmployeeDto savedEmployee = employeeService.updateStatus(employeeToSave);
        assertEquals(id, savedEmployee.getId());
        assertEquals(state, savedEmployee.getState());
    }

    @Test
    public void updateEmployee_ErrorNoEmployee() {
        String id = UUID.randomUUID().toString();
        int newAge = 21;
        EmployeeDto employeeToSave = new EmployeeDto();
        employeeToSave.setId(id);
        employeeToSave.setAge(newAge);
        when(employeeRepository.findById(eq(id))).thenReturn(Optional.empty());
        assertThrows(EmployeeServiceException.class, () -> employeeService.updateStatus(employeeToSave));
    }

    @Test
    public void updateEmployee_ErrorOfRepository() {
        String id = UUID.randomUUID().toString();
        int oldAge = 20;
        int newAge = 21;
        EmployeeDto employeeToSave = new EmployeeDto();
        employeeToSave.setId(id);
        employeeToSave.setAge(newAge);
        Employee employeeEntity = new Employee();
        employeeEntity.setId(id);
        employeeEntity.setAge(oldAge);
        when(employeeRepository.findById(eq(id))).thenReturn(Optional.of(employeeEntity));
        when(employeeRepository.save(any(Employee.class))).thenThrow(RuntimeException.class);
        assertThrows(EmployeeServiceException.class, () -> employeeService.updateStatus(employeeToSave));
    }

}