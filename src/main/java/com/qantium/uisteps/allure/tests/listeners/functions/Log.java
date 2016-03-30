package com.qantium.uisteps.allure.tests.listeners.functions;

import com.qantium.uisteps.allure.tests.listeners.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.qantium.uisteps.allure.tests.listeners.Event.*;

/**
 * Created by Solan on 25.03.2016.
 */
public class Log extends ListenerFunction {

   // private Logger log = LoggerFactory.getLogger("");

    public Log() {
        super(new Event[]{STEP_STARTED});
    }

    @Override
    public String execute() {
        System.out.println(getListener().getTestResult().getName() + " " + getListener().getLastStep().getName());

     //   log.info(getListener().getLastStep().getName());
        return null;
    }
}
