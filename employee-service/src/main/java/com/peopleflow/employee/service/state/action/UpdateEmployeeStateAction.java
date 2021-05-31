package com.peopleflow.employee.service.state.action;

import com.peopleflow.employee.service.employee.EmployeeService;
import com.peopleflow.employee.service.state.StateEvent;
import com.peopleflow.employee.service.state.utils.StateMachineUtils;
import com.peopleflow.lib.EmployeeDto;
import com.peopleflow.lib.EmployeeState;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

@Slf4j
@AllArgsConstructor
public class UpdateEmployeeStateAction implements Action<EmployeeState, StateEvent> {

    private EmployeeService employeeService;

    private StateMachineUtils stateMachineUtils;

    @Override
    public void execute(StateContext<EmployeeState, StateEvent> stateContext) {
        String employeeId = stateMachineUtils.getEmployeeId(stateContext.getStateMachine());
        EmployeeState state = getNewState(stateContext);
        EmployeeDto employee = new EmployeeDto();
        employee.setState(state);
        employee.setId(employeeId);
        log.info("Update state of employee '{}' to '{}'", employeeId, state);
        employeeService.updateStatus(employee);
    }

    protected EmployeeState getNewState(StateContext<EmployeeState, StateEvent> stateContext) {
        return stateContext.getTarget().getId();
    }
}
