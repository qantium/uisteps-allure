package com.qantium.uisteps.allure.tests.listeners.handlers;

import com.qantium.uisteps.allure.tests.listeners.Event;
import ru.yandex.qatools.allure.model.Status;
import ru.yandex.qatools.allure.model.TestCaseResult;

import static com.qantium.uisteps.allure.tests.listeners.Event.TEST_FINISHED;
import static ru.yandex.qatools.allure.model.Status.*;

/**
 * Created by Anton Solyankin
 */
public class SetTestStatus extends EventHandler {

    public SetTestStatus() {
        super(new Event[]{TEST_FINISHED});
    }


    @Override
    public Status handle(Event event) {
        Throwable error = getListener().getError();
        TestCaseResult testCase = getListener().getTestCase();
        if(error != null) {
            if(error instanceof AssertionError) {
                testCase.setStatus(FAILED);
            } else {
                testCase.setStatus(BROKEN);
            }
        } else {
            Status status = testCase.getStatus();
            if (status != CANCELED && status != PENDING && status != SKIPPED) {
                testCase.setStatus(PASSED);
            }
        }
        return testCase.getStatus();
    }
}
