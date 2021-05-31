package com.peopleflow.employee.service.employee;

import com.peopleflow.employee.dao.ErrorRepository;
import com.peopleflow.employee.entity.Employee;
import com.peopleflow.employee.entity.EmployeeError;
import com.peopleflow.employee.service.employee.ErrorStorageService;
import com.peopleflow.lib.EmployeeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ErrorStorageServiceTest {

    @MockBean
    private ErrorRepository errorRepository;

    private ErrorStorageService errorStorageService;

    @BeforeEach
    public void init() {
        errorStorageService = new ErrorStorageService(errorRepository);
    }

    @Test
    public void saveError() {
        ThreadLocal<EmployeeError> objectKeeper = new ThreadLocal<>();
        String id = UUID.randomUUID().toString();
        String errorMessage = "error message";
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(id);


        when(errorRepository.save(any(EmployeeError.class))).thenAnswer((Answer<EmployeeError>) invocation -> {
            EmployeeError error = invocation.getArgument(0);
            objectKeeper.set(error);
            return error;
        });
        errorStorageService.process(employeeDto, new Exception(errorMessage));

        assertEquals(id, objectKeeper.get().getEmployeeId());
        assertEquals(errorMessage, objectKeeper.get().getErrorMessage());
        assertNotNull(objectKeeper.get().getDate());
    }
}