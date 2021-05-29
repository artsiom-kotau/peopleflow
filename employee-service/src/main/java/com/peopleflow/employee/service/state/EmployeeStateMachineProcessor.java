package com.peopleflow.employee.service.state;

import com.peopleflow.lib.EmployeeDto;
import com.peopleflow.lib.EmployeeState;
import lombok.AllArgsConstructor;
import org.springframework.statemachine.StateMachine;

import java.util.Objects;

@AllArgsConstructor
public class EmployeeStateMachineProcessor implements StateProcessor {

    private StateMachineStorage stateMachineStorage;

    @Override
    public void process(EmployeeDto employee) {
        String employeeId = employee.getId();
        StateMachine<EmployeeState, StateEvent> stateMachine = stateMachineStorage.getStateMachine(employeeId);
        StateMachineUtils.setEmployee(stateMachine, employee);
        stateMachine.sendEvent(EmployeeStateEventMapper.getEventForState(employee.getState()));
        stateMachineStorage.saveStateMachine(employeeId, stateMachine);
    }
}
