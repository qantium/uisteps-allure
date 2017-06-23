package com.qantium.uisteps.allure.tests.listeners.handlers;

import com.qantium.uisteps.allure.tests.listeners.Event;
import ru.yandex.qatools.allure.model.Step;

import java.util.Deque;
import java.util.Iterator;

import static com.qantium.uisteps.allure.tests.listeners.Event.ASSERT;
import static com.qantium.uisteps.allure.tests.listeners.Event.TEST_FINISHED;
import static ru.yandex.qatools.allure.model.Status.FAILED;

/**
 * Created by Anton Solyankin
 */
public class CatchAssertions extends EventHandler {

    private boolean testIsFailed;

    public CatchAssertions() {
        super(new Event[]{ASSERT, TEST_FINISHED});
    }

    @Override
    public Object handle(Event event, Object... args) {

        switch (event) {
            case ASSERT:
                return handleAssert(args);
            default:
                if (testIsFailed) {
                    getListener().getTestCase().setStatus(FAILED);
                }
                return null;
        }
    }

    private Object handleAssert(Object... args) {
        testIsFailed = true;
        Deque<Step> stepStorage = getListener().getStepStorage().get();
        Iterator<Step> iterator = stepStorage.iterator();

        skipRootStep(iterator);

        while (iterator.hasNext()) {
            Step parentStep = iterator.next();

            fail(parentStep);
        }
        return null;
    }

    private void fail(Step step) {
        step.setStatus(FAILED);
    }

    private void skipRootStep(Iterator<Step> iterator) {
        if (iterator.hasNext()) {
            iterator.next();
        }
    }
}
