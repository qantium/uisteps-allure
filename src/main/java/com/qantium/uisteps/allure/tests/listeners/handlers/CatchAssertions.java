package com.qantium.uisteps.allure.tests.listeners.handlers;

import com.qantium.uisteps.allure.tests.listeners.Event;
import ru.yandex.qatools.allure.model.Step;

import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

import static com.qantium.uisteps.allure.tests.listeners.Event.ASSERT;
import static com.qantium.uisteps.allure.tests.listeners.Event.TEST_FINISHED;
import static ru.yandex.qatools.allure.model.Status.FAILED;

/**
 * Created by Anton Solyankin
 */
public class CatchAssertions extends EventHandler {

    private StringBuilder messages = new StringBuilder();


    public CatchAssertions() {
        super(new Event[]{ASSERT, TEST_FINISHED});
    }

    @Override
    public String handle(Event event, Object... args) {

        switch (event) {
            case ASSERT:
                String message = args[0].toString();
                messages.append(message).append("; ");

                Step step = getListener().getLastStep();
                Step messageStep = new Step();
                messageStep.setTitle(message);
                fail(messageStep);

                List<Step> steps = new ArrayList();
                steps.add(messageStep);
                step.setSteps(steps);
                fail(step);

                Deque<Step> stepStorage = getListener().getStepStorage().get();
                Iterator<Step> iterator = stepStorage.iterator();

                skipRootStep(iterator);

                while (iterator.hasNext()) {
                    Step parentStep = iterator.next();

                    fail(parentStep);
                    if (parentStep.getSteps().contains(step)) {
                        break;
                    }
                }

                return message;
            default:
                if (messages.length() > 0) {
                    getListener().getTestCase().setStatus(FAILED);
                }
                return messages.toString();
        }

    }

    private void fail(Step step) {
        step.setStatus(FAILED);
    }

    private void skipRootStep(Iterator<Step> iterator) {
        if(iterator.hasNext()) {
            iterator.next();
        }
    }
}
