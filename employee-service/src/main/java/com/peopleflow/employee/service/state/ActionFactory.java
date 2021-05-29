package com.peopleflow.employee.service.state;

import com.peopleflow.employee.service.employee.EmployeeService;
import com.peopleflow.lib.EmployeeState;
import lombok.Getter;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.guard.Guard;

@Getter
public class ActionFactory {

    private Action<EmployeeState, StateEvent> updateStateAction;

    private Action<EmployeeState, StateEvent> createAction;

    private Guard<EmployeeState, StateEvent> guard;

    public ActionFactory(EmployeeService employeeService) {
        this.updateStateAction = new UpdateEmployeeStateAction(employeeService);
        this.createAction = new CreateEmployeeAction(employeeService);
        this.guard = new StateMachineGuard(employeeService);

    }
}
