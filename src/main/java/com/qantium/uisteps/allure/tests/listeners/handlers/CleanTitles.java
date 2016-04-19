package com.qantium.uisteps.allure.tests.listeners.handlers;

import com.qantium.uisteps.allure.tests.listeners.Event;
import static com.qantium.uisteps.allure.tests.listeners.Event.*;
import com.qantium.uisteps.core.lifecycle.MetaInfo;
import ru.yandex.qatools.allure.model.Step;
import ru.yandex.qatools.allure.model.TestCaseResult;

import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * Created by Anton Solyankin
 */
public class CleanTitles extends EventHandler {

    public CleanTitles() {
        super(new Event[]{TEST_FINISHED});
    }

    @Override
    public Object handle(Event event) {
        for (Step step : getListener().getSteps()) {
            clearTitleOf(step);
        }
        clearTitleOf(getListener().getTestCase());
        return null;
    }

    private void clearTitleOf(Step step) {
        String stepTitle = step.getTitle();
        if (!isEmpty(stepTitle)) {
            step.setTitle(new MetaInfo(stepTitle).getTitleWithoutMeta());
        }
    }

    private void clearTitleOf(TestCaseResult test) {
        String testTitle = test.getTitle();
        if (!isEmpty(testTitle)) {
            test.setTitle(new MetaInfo(testTitle).getTitleWithoutMeta());
        }
    }
}