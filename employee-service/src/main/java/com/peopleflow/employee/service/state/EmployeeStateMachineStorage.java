package com.peopleflow.employee.service.state;

import com.peopleflow.lib.EmployeeState;
import lombok.AllArgsConstructor;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachinePersister;

@AllArgsConstructor
public class EmployeeStateMachineStorage implements StateMachineStorage {


    private StateMachinePersister<EmployeeState, StateEvent, String> persister;

    private StateMachineFactory<EmployeeState, StateEvent> stateMachineFactory;

    @Override
    public StateMachine<EmployeeState, StateEvent> getStateMachine(String employeeId) {
        StateMachine<EmployeeState, StateEvent> stateMachine = stateMachineFactory.getStateMachine();
        try {
            return persister.restore(stateMachine, employeeId);
        } catch (Exception exc) {
            throw  new RuntimeException(exc);
        }

    }

    @Override
    public void saveStateMachine(String employeeId, StateMachine<EmployeeState, StateEvent> stateMachine) {
        try {
            persister.persist(stateMachine, employeeId);
        } catch (Exception exc) {
            throw  new RuntimeException(exc);
        }
    }
}
