package com.peopleflow.employee.service.state;

import com.peopleflow.employee.service.state.persist.StateMachineStorage;
import com.peopleflow.employee.service.state.utils.StateMachineUtils;
import com.peopleflow.lib.EmployeeDto;
import com.peopleflow.lib.EmployeeState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.statemachine.StateMachine;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class EmployeeStateMachineProcessorTest {

    @MockBean
    private StateMachineStorage stateMachineStorage;

    private EmployeeStateMachineProcessor  employeeStateMachineProcessor;

    @BeforeEach
    public void init() {
        employeeStateMachineProcessor = new EmployeeStateMachineProcessor(stateMachineStorage);
        employeeStateMachineProcessor.setStateMachineUtils(mock(StateMachineUtils.class));
    }

    @Test
    public void testEventSendAndStateMachinePersist() {
        String employeeId = "1223";
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employeeId);
        StateMachine<EmployeeState, StateEvent> stateMachine = mock(StateMachine.class);
        when(stateMachineStorage.getStateMachine(eq(employeeId))).thenReturn(stateMachine);

        employeeStateMachineProcessor = spy(employeeStateMachineProcessor);

        employeeStateMachineProcessor.process(employeeDto);
        verify(stateMachine, times(1)).sendEvent(any(StateEvent.class));
        verify(stateMachineStorage, times(1)).saveStateMachine(eq(employeeId), same(stateMachine));
    }

}