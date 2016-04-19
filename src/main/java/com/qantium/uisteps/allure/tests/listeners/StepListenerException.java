package com.qantium.uisteps.allure.tests.listeners;

import com.qantium.uisteps.allure.tests.listeners.handlers.EventHandler;

/**
 * Created by Anton Solyankin
 */
public class StepListenerException extends RuntimeException {

    public StepListenerException(EventHandler handler, Throwable cause) {
        super("Handler " + handler.getClass().getSimpleName() + " was failed in step listener!", cause);
    }
}
