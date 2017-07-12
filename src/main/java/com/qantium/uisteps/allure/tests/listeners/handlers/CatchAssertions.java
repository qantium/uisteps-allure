package com.qantium.uisteps.allure.tests.listeners.handlers;

import com.qantium.uisteps.allure.tests.listeners.Event;
import ru.yandex.qatools.allure.model.Step;

import java.util.Deque;
import java.util.Iterator;

import static com.qantium.uisteps.allure.tests.listeners.Event.*;
import static ru.yandex.qatools.allure.model.Status.BROKEN;
import static ru.yandex.qatools.allure.model.Status.FAILED;

/**
 * Created by Anton Solyankin
 */
public class CatchAssertions extends EventHandler {

    private boolean testIsFailed;
    private boolean testIsBroken;

    public CatchAssertions() {
        super(new Event[]{ASSERT, ASSERT_BROKEN, TEST_FINISHED});
    }

    @Override
    public Object handle(Event event, Object... args) {

        switch (event) {
            case ASSERT:
            case ASSERT_BROKEN:
                return handleAssert(event);
            default:
                if (testIsFailed) {
                    getListener().getTestCase().setStatus(FAILED);
                } else if (testIsBroken) {
                    getListener().getTestCase().setStatus(BROKEN);
                }
                return null;
        }
    }

    private Object handleAssert(Event event) {
        if (event == ASSERT) {
            testIsFailed = true;
        } else {
            testIsBroken = true;
        }

        Deque<Step> stepStorage = getListener().getStepStorage().get();
        Iterator<Step> iterator = stepStorage.iterator();

        skipRootStep(iterator);

        while (iterator.hasNext()) {
            Step parentStep = iterator.next();

            fail(event, parentStep);
        }
        return null;
    }

    private void fail(Event event, Step step) {
        if (event == ASSERT) {
            step.setStatus(FAILED);
        } else if (step.getStatus() != FAILED) {
            step.setStatus(BROKEN);
        }
    }

    private void skipRootStep(Iterator<Step> iterator) {
        if (iterator.hasNext()) {
            iterator.next();
        }
    }
}
