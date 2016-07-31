package com.qantium.uisteps.allure.tests;

import com.qantium.uisteps.allure.tests.listeners.StepListener;
import com.qantium.uisteps.allure.tests.listeners.handlers.*;
import com.qantium.uisteps.allure.user.User;
import com.qantium.uisteps.core.screenshots.IPhotographer;
import com.qantium.uisteps.core.screenshots.Photographer;
import ru.yandex.qatools.allure.Allure;

/**
 * Created by Anton Solyankin
 */
public class BaseTest extends User {

    public BaseTest() {
        addListener(initListener());
    }

    private void addListener(StepListener listener) {
        listener.getStepStorage().get().clear();
        Allure.LIFECYCLE.addListener(listener);
    }

    protected StepListener initListener() {
        StepListener listener = new StepListener(this);

        listener
                .add(new CatchErrors())
                .add(new TakeScreenshot())
                .add(new TakePageSource())
                .add(new CloseBrowsers())
                .add(new CleanTitles())
                .add(new SetTestStatus())
                .add(new LogTests());
//
//        if (TestRailAdapter.getInstance().isDefined()) {
//            listener.add(new ReportTestRail());
//        }
        return listener;
    }

    public IPhotographer getPhotographer() {
        return new Photographer(getDriver());
    }
}
