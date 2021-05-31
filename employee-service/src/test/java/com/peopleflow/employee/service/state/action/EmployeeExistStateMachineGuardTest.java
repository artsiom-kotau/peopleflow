package com.peopleflow.employee.service.state.action;

import com.peopleflow.employee.service.employee.EmployeeService;
import com.peopleflow.employee.service.state.StateEvent;
import com.peopleflow.employee.service.state.utils.StateMachineUtils;
import com.peopleflow.lib.EmployeeDto;
import com.peopleflow.lib.EmployeeState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class EmployeeExistStateMachineGuardTest {

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private StateMachineUtils stateMachineUtils;

    private EmployeeExistStateMachineGuard employeeExistStateMachineGuard;

    @BeforeEach
    public void init() {
        this.employeeExistStateMachineGuard = new EmployeeExistStateMachineGuard(employeeService, stateMachineUtils);
    }

    @Test
    public void userExist() {
        String employeeId = "11111";
        StateMachine<EmployeeState, StateEvent> stateMachine = mock(StateMachine.class);
        StateContext<EmployeeState, StateEvent> stateContext = mock(StateContext.class);

        when(stateContext.getStateMachine()).thenReturn(stateMachine);
        when(stateMachineUtils.getEmployeeId(same(stateMachine))).thenReturn(employeeId);
        when(employeeService.get(eq(employeeId))).thenReturn(new EmployeeDto());
        assertTrue(employeeExistStateMachineGuard.evaluate(stateContext));
    }

    @Test
    public void userNotExist() {

        String employeeId = "11111";
        StateMachine<EmployeeState, StateEvent> stateMachine = mock(StateMachine.class);
        StateContext<EmployeeState, StateEvent> stateContext = mock(StateContext.class);

        when(stateContext.getStateMachine()).thenReturn(stateMachine);
        when(stateMachineUtils.getEmployeeId(same(stateMachine))).thenReturn(employeeId);
        when(employeeService.get(eq(employeeId))).thenReturn(null);
        assertFalse(employeeExistStateMachineGuard.evaluate(stateContext));

    }

}