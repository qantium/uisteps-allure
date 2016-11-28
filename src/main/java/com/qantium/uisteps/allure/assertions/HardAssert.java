package com.qantium.uisteps.allure.assertions;

import com.qantium.uisteps.allure.tests.listeners.StepListener;

import static com.qantium.uisteps.allure.tests.listeners.Event.STEP_FAILED;


/**
 * Created by Anton Solyankin
 */
public class HardAssert extends SoftAssert {

    public HardAssert(StepListener listener) {
        super(listener);
    }

    public void error(String message) throws AssertionError {
        super.error(message);
        getListener().fire(STEP_FAILED, message);
    }
}
