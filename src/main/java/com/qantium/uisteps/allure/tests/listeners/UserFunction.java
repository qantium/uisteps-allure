package com.qantium.uisteps.allure.tests.listeners;

import com.qantium.uisteps.allure.user.User;
import com.qantium.uisteps.core.properties.UIStepsProperties;
import org.apache.commons.lang.ArrayUtils;

import java.util.*;

/**
 * Created by Anton Solyankin
 */
public abstract class UserFunction<E> {

    private final User user;
    private final Set<Event> events = new HashSet();
    private final String property;

    public UserFunction(User user, String property) {
        this(user, property, null);
    }

    public UserFunction(User user, String property, Event[] events) {
        this.user = user;
        this.property = property;

        if (ArrayUtils.isEmpty(events)) {
            this.events.add(Event.STEP_FAILED);

            switch (UIStepsProperties.getProperty(property)) {
                case "BEFORE_AND_AFTER_EACH_STEP":
                    this.events.add(Event.STEP_STARTED);
                    this.events.add(Event.STEP_FINISHED);
                    break;
                case "BEFORE_EACH_STEP":
                    this.events.add(Event.STEP_STARTED);
                    break;
                case "AFTER_EACH_STEP":
                    this.events.add(Event.STEP_FINISHED);
                    break;
            }

        } else {
            this.events.addAll(Arrays.asList(events));
        }
    }

    public String getProperty() {
        return property;
    }

    public User getUser() {
        return user;
    }

    public boolean needsOn(Event phase) {
        return events.contains(phase);
    }

    public abstract E execute();


}
