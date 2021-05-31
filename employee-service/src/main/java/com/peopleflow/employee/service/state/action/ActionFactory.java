package com.peopleflow.employee.service.state.action;

import com.peopleflow.employee.service.employee.EmployeeService;
import com.peopleflow.employee.service.state.StateEvent;
import com.peopleflow.employee.service.state.utils.StateMachineUtils;
import com.peopleflow.lib.EmployeeState;
import lombok.Getter;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.guard.Guard;

@Getter
public class ActionFactory {

    private Action<EmployeeState, StateEvent> updateStateAction;

    private Action<EmployeeState, StateEvent> createAction;

    private Guard<EmployeeState, StateEvent> guard;

    public ActionFactory(EmployeeService employeeService, StateMachineUtils stateMachineUtils) {
        this.updateStateAction = new UpdateEmployeeStateAction(employeeService, stateMachineUtils);
        this.createAction = new CreateEmployeeAction(employeeService, stateMachineUtils);
        this.guard = new EmployeeExistStateMachineGuard(employeeService, stateMachineUtils);

    }
}
