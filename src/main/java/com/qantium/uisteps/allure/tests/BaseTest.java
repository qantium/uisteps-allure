package com.qantium.uisteps.allure.tests;

import com.qantium.uisteps.allure.tests.listeners.StepListener;
import com.qantium.uisteps.allure.tests.listeners.handlers.*;
import com.qantium.uisteps.allure.user.User;
import ru.yandex.qatools.allure.Allure;


/**
 * Created by Anton Solyankin
 */
public class BaseTest extends User {

    private final StepListener listener;

    public BaseTest() {
        listener = new StepListener(this);

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

        Allure.LIFECYCLE.addListener(listener);
    }

    public StepListener getListener() {
        return listener;
    }
}
