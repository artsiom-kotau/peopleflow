package com.peopleflow.config;

import com.peopleflow.employee.service.state.ActionFactory;
import com.peopleflow.employee.service.state.StateEvent;
import com.peopleflow.lib.EmployeeState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Configuration
@EnableStateMachineFactory
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<EmployeeState, StateEvent> {

    @Autowired
    private ActionFactory actionFactory;

    @Override
    public void configure(final StateMachineConfigurationConfigurer<EmployeeState, StateEvent> config) throws Exception {
        config
                .withConfiguration()
                .autoStartup(true);
    }

    @Override
    public void configure(StateMachineStateConfigurer<EmployeeState, StateEvent> states) throws Exception {
        states
                .withStates()
                .initial(EmployeeState.NEW)
                .end(EmployeeState.ACTIVE)
                .states(EnumSet.allOf(EmployeeState.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<EmployeeState, StateEvent> transitions) throws Exception {
        transitions

                .withExternal()
                .source(EmployeeState.NEW)
                .target(EmployeeState.ADDED)
                .event(StateEvent.CREATE)
                .action(actionFactory.getCreateAction())
                .and()

                .withExternal()
                .source(EmployeeState.ADDED)
                .target(EmployeeState.IN_CHECK)
                .event(StateEvent.START_CHECK)
                .guard(actionFactory.getGuard())
                .action(actionFactory.getUpdateStateAction())

                .and()
                .withExternal()
                .source(EmployeeState.IN_CHECK)
                .target(EmployeeState.APPROVED)
                .event(StateEvent.FINISH_CHECK)
                .guard(actionFactory.getGuard())
                .action(actionFactory.getUpdateStateAction())

                .and()
                .withExternal()
                .source(EmployeeState.APPROVED)
                .target(EmployeeState.ACTIVE)
                .event(StateEvent.ACTIVATE)
                .guard(actionFactory.getGuard())
                .action(actionFactory.getUpdateStateAction());
    }
}

