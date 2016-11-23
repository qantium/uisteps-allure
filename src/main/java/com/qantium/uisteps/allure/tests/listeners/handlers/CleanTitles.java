package com.qantium.uisteps.allure.tests.listeners.handlers;

import com.qantium.uisteps.allure.tests.listeners.Event;
import com.qantium.uisteps.core.lifecycle.MetaInfo;
import ru.yandex.qatools.allure.model.Step;
import ru.yandex.qatools.allure.model.TestCaseResult;

import static com.qantium.uisteps.allure.tests.listeners.Event.TEST_FINISHED;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * Created by Anton Solyankin
 */
public class CleanTitles extends EventHandler {

    public CleanTitles() {
        super(new Event[]{TEST_FINISHED});
    }

    @Override
    public Object handle(Event event, Object... args) {
        for (Step step : getListener().getSteps()) {
            clearTitleOf(step);
        }
        clearTitleOf(getListener().getTestCase());
        return null;
    }

    private void clearTitleOf(Step step) {
        String stepTitle = step.getTitle();
        if (isNotEmpty(stepTitle)) {
            step.setTitle(new MetaInfo(stepTitle).getTitleWithoutMeta());
        }
    }

    private void clearTitleOf(TestCaseResult test) {
        String testTitle = test.getTitle();
        if (isNotEmpty(testTitle)) {
            test.setTitle(new MetaInfo(testTitle).getTitleWithoutMeta());
        }
    }
}