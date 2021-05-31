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

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class UpdateEmployeeStateActionTest {

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private StateMachineUtils stateMachineUtils;

    private UpdateEmployeeStateAction updateEmployeeStateAction;

    @BeforeEach
    public void init() {
        this.updateEmployeeStateAction = new UpdateEmployeeStateAction(employeeService, stateMachineUtils);
    }

    @Test
    public void testUpdateEmployeeAction() {
        EmployeeDto employeeDto = new EmployeeDto();
        StateMachine<EmployeeState, StateEvent> stateMachine = mock(StateMachine.class);
        StateContext<EmployeeState, StateEvent> stateContext = mock(StateContext.class);
        when(stateContext.getStateMachine()).thenReturn(stateMachine);
        when(stateMachineUtils.getEmployee(eq(stateMachine))).thenReturn(employeeDto);
        updateEmployeeStateAction = spy(updateEmployeeStateAction);
        doReturn(EmployeeState.APPROVED).when(updateEmployeeStateAction).getNewState(same(stateContext));

        updateEmployeeStateAction.execute(stateContext);

        verify(employeeService, times(1)).updateStatus(any(EmployeeDto.class));
    }

}