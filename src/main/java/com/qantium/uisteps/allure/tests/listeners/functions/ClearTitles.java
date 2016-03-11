package com.qantium.uisteps.allure.tests.listeners.functions;

import com.qantium.uisteps.allure.tests.listeners.Event;
import com.qantium.uisteps.core.tests.MetaInfo;
import org.apache.commons.lang3.StringUtils;
import ru.yandex.qatools.allure.model.Step;
import ru.yandex.qatools.allure.model.TestCaseResult;

/**
 * Created by Anton Solyankin
 */
public class ClearTitles extends ListenerFunction {

    public ClearTitles() {
        super(new Event[]{Event.TEST_FINISHED});
    }

    @Override
    public Object execute() {
        for(Step step: getListener().getSteps()) {
            clearTitleOf(step);
        }
        clearTitleOf(getListener().getTest());
        return null;
    }

    private void clearTitleOf(Step step) {
        String stepTitle = step.getTitle();
        if (!StringUtils.isEmpty(stepTitle)) {
            step.setTitle(new MetaInfo(stepTitle).getTitleWithoutMeta());
        }
    }

    private void clearTitleOf(TestCaseResult test) {
        String testTitle = test.getTitle();
        if (!StringUtils.isEmpty(testTitle)) {
            test.setTitle(new MetaInfo(testTitle).getTitleWithoutMeta());
        }
    }
}
