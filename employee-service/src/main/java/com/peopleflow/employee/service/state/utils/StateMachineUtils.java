package com.peopleflow.employee.service.state.utils;

import com.peopleflow.employee.service.state.StateEvent;
import com.peopleflow.lib.EmployeeDto;
import com.peopleflow.lib.EmployeeState;
import org.springframework.statemachine.StateMachine;

import java.util.Objects;

public class StateMachineUtils {
    private static final String EMPLOYEE_OBJECT = "employee";

    public void setEmployee(StateMachine<EmployeeState, StateEvent> stateMachine, EmployeeDto employee) {
        stateMachine.getExtendedState().getVariables().put(EMPLOYEE_OBJECT, employee);
    }

    public EmployeeDto getEmployee(StateMachine<EmployeeState, StateEvent> stateMachine) {
        Object employee = stateMachine.getExtendedState().getVariables().get(EMPLOYEE_OBJECT);
        return (EmployeeDto) employee;
    }

    public String getEmployeeId(StateMachine<EmployeeState, StateEvent> stateMachine) {
        EmployeeDto employee = (EmployeeDto) stateMachine.getExtendedState().getVariables().get(EMPLOYEE_OBJECT);
        return Objects.nonNull(employee) ? employee.getId() : null;
    }
}
