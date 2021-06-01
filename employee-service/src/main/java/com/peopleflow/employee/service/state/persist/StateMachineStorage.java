package com.peopleflow.employee.service.state.persist;

import com.peopleflow.employee.service.state.StateEvent;
import com.peopleflow.lib.EmployeeState;
import org.springframework.statemachine.StateMachine;

public interface StateMachineStorage {

    StateMachine<EmployeeState, StateEvent> getStateMachine(String employeeId);

    void saveStateMachine(String employeeId, StateMachine<EmployeeState, StateEvent> stateMachine);
}
