package com.peopleflow.api;

import com.peopleflow.exception.EmployeeNotFoundException;
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

import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EmployeeStateController.class)
class EmployeeStateControllerTest extends ApiTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EmployeeService employeeService;

    @Test
    public void checkEmployeeStatus_EmployeeNotFound() throws Exception {

        String employeeId = "1111";
        when(employeeService.status(eq(employeeId))).thenThrow(new EmployeeNotFoundException(employeeId));

        mvc.perform(get("/employee/{id}/status", employeeId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.id", Is.is(employeeId)));
    }

    @Test
    public void checkEmployeeStatus_GenericError() throws Exception {

        String message = "some error";
        String employeeId = "1111";
        when(employeeService.status(eq(employeeId))).thenThrow(new RuntimeException(message));

        mvc.perform(get("/employee/{id}/status", employeeId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error", Is.is(message)));
    }

    @Test
    public void checkEmployeeStatus_Success() throws Exception {

        String employeeId = "1111";
        EmployeeState state = EmployeeState.APPROVED;
        when(employeeService.status(eq(employeeId))).thenReturn(state);

        mvc.perform(get("/employee/{id}/status", employeeId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Is.is(employeeId)))
                .andExpect(jsonPath("$.state", Is.is(state.getLabel())));
    }

    @Test
    public void checkingEmployee_EmployeeNotFound() throws Exception {

        String employeeId = "1111";
        when(employeeService.check(eq(employeeId))).thenThrow(new EmployeeNotFoundException(employeeId));

        mvc.perform(post("/employee/{id}/checking", employeeId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.id", Is.is(employeeId)));
    }

    @Test
    public void checkingEmployee_GenericError() throws Exception {

        String message = "some error";
        String employeeId = "1111";
        when(employeeService.check(eq(employeeId))).thenThrow(new RuntimeException(message));

        mvc.perform(post("/employee/{id}/checking", employeeId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error", Is.is(message)));
    }

    @Test
    public void checkingEmployee_Success() throws Exception {

        String employeeId = "1111";
        EmployeeState state = EmployeeState.IN_CHECK;
        when(employeeService.check(eq(employeeId))).thenReturn(state);

        mvc.perform(post("/employee/{id}/checking", employeeId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Is.is(employeeId)))
                .andExpect(jsonPath("$.state", Is.is(state.getLabel())));
    }

    @Test
    public void approveEmployee_EmployeeNotFound() throws Exception {

        String employeeId = "1111";
        when(employeeService.approve(eq(employeeId))).thenThrow(new EmployeeNotFoundException(employeeId));

        mvc.perform(post("/employee/{id}/approve", employeeId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.id", Is.is(employeeId)));
    }

    @Test
    public void approveEmployee_GenericError() throws Exception {

        String message = "some error";
        String employeeId = "1111";
        when(employeeService.approve(eq(employeeId))).thenThrow(new RuntimeException(message));

        mvc.perform(post("/employee/{id}/approve", employeeId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error", Is.is(message)));
    }

    @Test
    public void approveEmployee_Success() throws Exception {

        String employeeId = "1111";
        EmployeeState state = EmployeeState.APPROVED;
        when(employeeService.approve(eq(employeeId))).thenReturn(state);

        mvc.perform(post("/employee/{id}/approve", employeeId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Is.is(employeeId)))
                .andExpect(jsonPath("$.state", Is.is(state.getLabel())));
    }

    @Test
    public void activateEmployee_EmployeeNotFound() throws Exception {

        String employeeId = "1111";
        when(employeeService.activate(eq(employeeId))).thenThrow(new EmployeeNotFoundException(employeeId));

        mvc.perform(post("/employee/{id}/activate", employeeId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.id", Is.is(employeeId)));
    }

    @Test
    public void activateEmployee_GenericError() throws Exception {

        String message = "some error";
        String employeeId = "1111";
        when(employeeService.activate(eq(employeeId))).thenThrow(new RuntimeException(message));

        mvc.perform(post("/employee/{id}/activate", employeeId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error", Is.is(message)));
    }

    @Test
    public void activateEmployee_Success() throws Exception {

        String employeeId = "1111";
        EmployeeState state = EmployeeState.APPROVED;
        when(employeeService.activate(eq(employeeId))).thenReturn(state);

        mvc.perform(post("/employee/{id}/activate", employeeId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Is.is(employeeId)))
                .andExpect(jsonPath("$.state", Is.is(state.getLabel())));
    }

}