package com.peopleflow.api;

import com.peopleflow.lib.EmployeeDto;
import com.peopleflow.lib.EmployeeState;
import com.peopleflow.service.EmployeeService;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest extends ApiTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EmployeeService employeeService;

    @Test
    public void postNewUserShouldReturn201() throws Exception {

        String newId = "1111";
        EmployeeDto addedEmployee = new EmployeeDto();
        addedEmployee.setId(newId);
        when(employeeService.addEmployee(any(EmployeeDto.class))).thenReturn(addedEmployee);

        mvc.perform(post("/employee").content("{}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id", Is.is(newId)));

        verify(employeeService, times(1)).addEmployee(any(EmployeeDto.class));

    }

    @Test
    public void genericErrorDuringPostShouldReturn500WithMessage() throws Exception {

        String message = "some error";
        when(employeeService.addEmployee(any(EmployeeDto.class))).thenThrow(new RuntimeException(message));

        mvc.perform(post("/employee").content("{}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error", Is.is(message)));

    }


    @Test
    public void updateEmployeeState_GenericError() throws Exception {

        String message = "some error";
        String employeeId = "1111";
        EmployeeState state = EmployeeState.APPROVED;
        when(employeeService.updateState(eq(employeeId), eq(state))).thenThrow(new RuntimeException(message));

        mvc.perform(post("/employee/{id}/updateStatus", employeeId)
                .content("{\"state\" :  \"APPROVED\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error", Is.is(message)));
    }

    @Test
    public void activateEmployee_Success() throws Exception {

        String employeeId = "1111";
        EmployeeState state = EmployeeState.APPROVED;
        when(employeeService.updateState(eq(employeeId), eq(state))).thenReturn(state);

        mvc.perform(post("/employee/{id}/updateStatus", employeeId)
                .content("{\"state\" :  \"APPROVED\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id", Is.is(employeeId)))
                .andExpect(jsonPath("$.state", Is.is(state.toString())));
    }

}