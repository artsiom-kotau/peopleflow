package com.peopleflow.employee.service.state.persist;

import com.peopleflow.employee.service.state.StateEvent;
import com.peopleflow.lib.EmployeeState;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.persist.StateMachinePersister;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryStateMachinePersister implements StateMachinePersist<EmployeeState, StateEvent, String> {

    private final Map<String, StateMachineContext<EmployeeState, StateEvent>> storage = new ConcurrentHashMap<>();

    @Override
    public void write(StateMachineContext<EmployeeState, StateEvent> context, String contextObj) throws Exception {
        storage.put(contextObj, context);
    }

    @Override
    public StateMachineContext<EmployeeState, StateEvent> read(String contextObj) throws Exception {
        return storage.get(contextObj);
    }
}
