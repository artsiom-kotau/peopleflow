package com.peopleflow.employee.service.state;

import com.peopleflow.employee.service.state.persist.StateMachineStorage;
import com.peopleflow.employee.service.state.utils.EmployeeStateEventMapper;
import com.peopleflow.employee.service.state.utils.StateMachineUtils;
import com.peopleflow.lib.EmployeeDto;
import com.peopleflow.lib.EmployeeState;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateMachine;

@Slf4j
@Setter
public class EmployeeStateMachineProcessor implements StateProcessor {

    private StateMachineUtils stateMachineUtils = new StateMachineUtils();

    private StateMachineStorage stateMachineStorage;

    public EmployeeStateMachineProcessor(StateMachineStorage stateMachineStorage) {
        this.stateMachineStorage = stateMachineStorage;
    }

    @Override
    public void process(EmployeeDto employee) {
        String employeeId = employee.getId();
        log.info("Start process employee '{}'", employeeId);
        StateMachine<EmployeeState, StateEvent> stateMachine = stateMachineStorage.getStateMachine(employeeId);
        stateMachineUtils.setEmployee(stateMachine, employee);
        stateMachine.sendEvent(EmployeeStateEventMapper.getEventForState(employee.getState()));
        stateMachineStorage.saveStateMachine(employeeId, stateMachine);
    }
}
