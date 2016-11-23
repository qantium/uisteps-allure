package com.qantium.uisteps.allure.tests.listeners.handlers;

import com.qantium.uisteps.allure.tests.listeners.Event;
import com.qantium.uisteps.allure.tests.listeners.StepListener;
import com.qantium.uisteps.core.lifecycle.Execute;
import com.qantium.uisteps.core.properties.IUIStepsProperty;
import com.qantium.uisteps.core.properties.UIStepsProperties;

import java.util.HashSet;
import java.util.Set;

import static com.qantium.uisteps.allure.tests.listeners.Event.*;
import static java.util.Arrays.asList;

/**
 * Created by Anton Solyankin
 */
public abstract class EventHandler {

    private final Set<Event> events = new HashSet();
    private StepListener listener ;

    public EventHandler(Event[] events) {
        this.events.addAll(asList(events));
    }

    public EventHandler(IUIStepsProperty property) {
        String[] executions = UIStepsProperties.getProperty(property).split(",");

        for (String execution : executions) {

            switch (Execute.valueOf(execution.trim().toUpperCase())) {
                case BEFORE_AND_AFTER_EACH_STEP:
                    events.add(STEP_STARTED);
                    events.add(STEP_FINISHED);
                    break;
                case BEFORE_EACH_STEP:
                    events.add(STEP_STARTED);
                    break;
                case AFTER_EACH_STEP:
                    events.add(STEP_FINISHED);
                    break;
                case FOR_FAILURES:
                    events.add(ASSERT);
                    events.add(STEP_FAILED);
                    break;
                case TEST_STARTED:
                    events.add(TEST_STARTED);
                    break;
                case TEST_FINISHED:
                    events.add(TEST_FINISHED);
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

    public abstract Object handle(Event event, Object... args);
}
