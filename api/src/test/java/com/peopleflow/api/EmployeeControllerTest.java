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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    public void toCheckEmployeeState_GenericError() throws Exception {

        String message = "some error";
        String employeeId = "1111";
        when(employeeService.check(eq(employeeId))).thenThrow(new RuntimeException(message));

        mvc.perform(post("/employee/{id}/check", employeeId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error", Is.is(message)));
    }

    @Test
    public void toCheckEmployee_Success() throws Exception {

        String employeeId = "1111";
        EmployeeState state = EmployeeState.APPROVED;
        when(employeeService.check(eq(employeeId))).thenReturn(state);

        mvc.perform(post("/employee/{id}/check", employeeId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id", Is.is(employeeId)))
                .andExpect(jsonPath("$.state", Is.is(state.toString())));
    }

    @Test
    public void toApproveEmployeeState_GenericError() throws Exception {

        String message = "some error";
        String employeeId = "1111";
        when(employeeService.approve(eq(employeeId))).thenThrow(new RuntimeException(message));

        mvc.perform(post("/employee/{id}/approve", employeeId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error", Is.is(message)));
    }

    @Test
    public void toApproveEmployee_Success() throws Exception {

        String employeeId = "1111";
        EmployeeState state = EmployeeState.APPROVED;
        when(employeeService.approve(eq(employeeId))).thenReturn(state);

        mvc.perform(post("/employee/{id}/approve", employeeId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id", Is.is(employeeId)))
                .andExpect(jsonPath("$.state", Is.is(state.toString())));
    }

    @Test
    public void toActivateEmployeeState_GenericError() throws Exception {

        String message = "some error";
        String employeeId = "1111";
        when(employeeService.activate(eq(employeeId))).thenThrow(new RuntimeException(message));

        mvc.perform(post("/employee/{id}/activate", employeeId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error", Is.is(message)));
    }

    @Test
    public void toActivateEmployee_Success() throws Exception {

        String employeeId = "1111";
        EmployeeState state = EmployeeState.APPROVED;
        when(employeeService.activate(eq(employeeId))).thenReturn(state);

        mvc.perform(post("/employee/{id}/activate", employeeId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id", Is.is(employeeId)))
                .andExpect(jsonPath("$.state", Is.is(state.toString())));
    }

    @Test
    public void getEmployeeState_GenericError() throws Exception {

        String message = "some error";
        String employeeId = "1111";
        when(employeeService.status(eq(employeeId))).thenThrow(new RuntimeException(message));

        mvc.perform(get("/employee/{id}/status", employeeId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error", Is.is(message)));
    }

    @Test
    public void getEmployeeState_NotFound() throws Exception {

        String employeeId = "1111";
        when(employeeService.status(eq(employeeId))).thenReturn(null);

        mvc.perform(get("/employee/{id}/status", employeeId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", Is.is("User with id \'1111\' not found")));
    }

    @Test
    public void getEmployee_Success() throws Exception {

        String employeeId = "1111";
        EmployeeState state = EmployeeState.APPROVED;
        when(employeeService.status(eq(employeeId))).thenReturn(state);

        mvc.perform(get("/employee/{id}/status", employeeId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Is.is(employeeId)))
                .andExpect(jsonPath("$.state", Is.is(state.toString())));
    }

}