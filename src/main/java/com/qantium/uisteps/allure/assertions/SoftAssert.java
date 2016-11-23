package com.qantium.uisteps.allure.assertions;

import com.qantium.uisteps.allure.tests.listeners.StepListener;

import static com.qantium.uisteps.allure.tests.listeners.Event.ASSERT;

/**
 * Created by Anton Solyankin
 */
public class SoftAssert {

    private final StepListener listener;
    private boolean not;

    public SoftAssert(StepListener listener) {
        this.listener = listener;
    }

    public void thatEqual(Object obj_1, Object obj_2, String message) {

        boolean condition = obj_1 == null && obj_2 == null;

        if (obj_1 != null) {
            condition = obj_1.equals(obj_2);
        }

        thatTrue(condition, message);
    }

    public void thatFalse(boolean condition, String message) {
        thatTrue(!condition, message);
    }

    public void thatTrue(boolean condition, String message) {
        if(isNot()) {
            condition = !condition;
        }

        if (!condition) {
            error(message);
        }

    }

    protected void error(String message) {
        getListener().fire(ASSERT, message);
        not(false);
    }

    public SoftAssert not() {
        return not(true);
    }

    public SoftAssert not(boolean not) {
        this.not = not;
        return this;
    }

    public StepListener getListener() {
        return listener;
    }

    public boolean isNot() {
        return not;
    }
}
