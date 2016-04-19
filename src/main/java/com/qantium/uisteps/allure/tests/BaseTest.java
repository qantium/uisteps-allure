package com.qantium.uisteps.allure.tests;

import com.qantium.uisteps.allure.tests.junit.RetryRule;
import com.qantium.uisteps.allure.tests.listeners.StepListener;
import com.qantium.uisteps.allure.tests.listeners.handlers.*;
import com.qantium.uisteps.allure.user.User;
import org.junit.Rule;
import ru.yandex.qatools.allure.Allure;
import ru.yandex.qatools.allure.annotations.Step;


/**
 * Created by Anton Solyankin
 */
public class BaseTest extends User {

    private final StepListener listener;

    public BaseTest() {
        listener = new StepListener(this);

        listener
                .add(new CatchErrors())
                .add(new LogTests())
                .add(new TakeScreenshot())
                .add(new TakePageSource())
                .add(new CloseBrowsers())
                .add(new CleanTitles())
                .add(new SetTestStatus());
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
