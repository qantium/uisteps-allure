package com.qantium.uisteps.allure.assertions;

import com.qantium.uisteps.allure.tests.listeners.StepListener;
import ru.yandex.qatools.allure.annotations.Step;

import static com.qantium.uisteps.allure.tests.listeners.Event.ASSERT;
import static com.qantium.uisteps.allure.tests.listeners.Event.ASSERT_BROKEN;

/**
 * Created by Anton Solyankin
 */
public class SoftAssert {

    private final StepListener listener;
    private boolean not;

    public SoftAssert(StepListener listener) {
        this.listener = listener;
    }

    public boolean thatEqual(Object obj_1, Object obj_2, String message, Object... args) {

        boolean condition = obj_1 == null && obj_2 == null;

        if (obj_1 != null) {
            condition = obj_1.equals(obj_2);
        }

        return thatTrue(condition, message, args);
    }

    public boolean thatFalse(boolean condition, String message, Object... args) {
        return thatTrue(!condition, message, args);
    }

    public boolean thatTrue(boolean condition, String message, Object... args) {
        if(isNot()) {
            condition = !condition;
        }

        if (!condition) {
            error(message, args);
        }
        not(false);
        return condition;
    }

    @Step("{0}")
    public void error(String message, Object... args) {
        getListener().fire(ASSERT, message, args);
    }

    @Step("{0}")
    public void broken(String message, Object... args) {
        getListener().fire(ASSERT_BROKEN, message, args);
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
