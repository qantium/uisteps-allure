package com.qantium.uisteps.allure.assertions;

import com.qantium.uisteps.allure.tests.listeners.StepListener;
import ru.yandex.qatools.allure.annotations.Step;

import static com.qantium.uisteps.allure.tests.listeners.Event.STEP_FAILED;


/**
 * Created by Anton Solyankin
 */
public class HardAssert extends SoftAssert {

    public HardAssert(StepListener listener) {
        super(listener);
    }

    @Step("{0}")
    public void error(String message, Object... args) throws AssertionError {
        getListener().fire(STEP_FAILED, message, args);
        not(false);
        throw new AssertionError(message);

    }
}
