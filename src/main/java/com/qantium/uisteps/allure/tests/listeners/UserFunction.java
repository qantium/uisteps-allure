package com.qantium.uisteps.allure.tests.listeners;

import com.qantium.uisteps.allure.user.User;
import com.qantium.uisteps.core.properties.UIStepsProperties;
import com.qantium.uisteps.core.tests.listeners.Execute;
import org.apache.commons.lang.ArrayUtils;

import java.util.*;

/**
 * Created by Anton Solyankin
 */
public abstract class UserFunction<E> {

    private final User user;
    private final Set<Event> events = new HashSet();

    public UserFunction(User user, Event[] events) {
        this.user = user;
        this.events.addAll(Arrays.asList(events));
    }


    public UserFunction(User user, String property) {
        this.user = user;

        String[] executions = UIStepsProperties.getProperty(property).split(",");
        for (String execution : executions) {
            switch (Execute.valueOf(execution.trim().toUpperCase())) {
                case BEFORE_AND_AFTER_EACH_STEP:
                    this.events.add(Event.STEP_STARTED);
                    this.events.add(Event.STEP_FINISHED);
                    break;
                case BEFORE_EACH_STEP:
                    this.events.add(Event.STEP_STARTED);
                    break;
                case AFTER_EACH_STEP:
                    this.events.add(Event.STEP_FINISHED);
                    break;
                case FOR_FAILURES:
                    this.events.add(Event.STEP_FAILED);
                    break;
            }
        }
    }

    public User getUser() {
        return user;
    }

    public boolean needsOn(Event phase) {
        return events.contains(phase);
    }

    public abstract E execute();
}
