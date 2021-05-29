package com.peopleflow.employee.service.state;

import com.peopleflow.lib.EmployeeDto;
import com.peopleflow.lib.EmployeeState;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateMachine;

@AllArgsConstructor
@Slf4j
public class EmployeeStateMachineProcessor implements StateProcessor {

    private StateMachineStorage stateMachineStorage;

    @Override
    public void process(EmployeeDto employee) {
        String employeeId = employee.getId();
        log.info("Start process employee '{}'", employeeId);
        StateMachine<EmployeeState, StateEvent> stateMachine = stateMachineStorage.getStateMachine(employeeId);
        StateMachineUtils.setEmployee(stateMachine, employee);
        stateMachine.sendEvent(EmployeeStateEventMapper.getEventForState(employee.getState()));
        stateMachineStorage.saveStateMachine(employeeId, stateMachine);
    }
}
