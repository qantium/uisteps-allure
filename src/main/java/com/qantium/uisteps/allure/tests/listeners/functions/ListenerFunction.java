package com.qantium.uisteps.allure.tests.listeners.functions;

import com.qantium.uisteps.allure.tests.listeners.Event;
import com.qantium.uisteps.allure.tests.listeners.StepListener;
import com.qantium.uisteps.core.properties.UIStepsProperties;
import com.qantium.uisteps.core.lifecycle.Execute;

import java.util.*;

/**
 * Created by Anton Solyankin
 */
public abstract class ListenerFunction {

    private final Set<Event> events = new HashSet();
    private StepListener listener ;

    public ListenerFunction(Event[] events) {
        this.events.addAll(Arrays.asList(events));
    }

    public ListenerFunction(String property) {
        String[] executions = UIStepsProperties.getProperty(property).split(",");

        for (String execution : executions) {

            switch (Execute.valueOf(execution.trim().toUpperCase())) {
                case BEFORE_AND_AFTER_EACH_STEP:
                    this.events.add(Event.STEP_FAILED);
                    this.events.add(Event.STEP_STARTED);
                    this.events.add(Event.STEP_FINISHED);
                    break;
                case BEFORE_EACH_STEP:
                    this.events.add(Event.STEP_STARTED);
                    break;
                case AFTER_EACH_STEP:
                    this.events.add(Event.STEP_FAILED);
                    this.events.add(Event.STEP_FINISHED);
                    break;
                case FOR_FAILURES:
                    this.events.add(Event.STEP_FAILED);
                    break;
                case TEST_STARTED:
                    this.events.add(Event.TEST_STARTED);
                    break;
                case TEST_FINISHED:
                    this.events.add(Event.TEST_FINISHED);
                    break;
            }
        }
    }

    public void setListener(StepListener listener) {
        this.listener = listener;
    }

    public StepListener getListener() {
        return listener;
    }

    public boolean needsOn(Event event) {
        return events.contains(event);
    }

    public abstract Object execute();
}
