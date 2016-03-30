package com.qantium.uisteps.allure.tests;

import com.qantium.uisteps.allure.tests.listeners.StepListener;
import com.qantium.uisteps.allure.tests.listeners.functions.*;
import com.qantium.uisteps.allure.user.User;
import com.qantium.uisteps.core.utils.testrail.TestRailAdapter;
import ru.yandex.qatools.allure.Allure;

/**
 * Created by Anton Solyankin
 */
public class BaseTest extends User {

    public BaseTest() {
        initListeners();
    }

    protected void initListeners() {
        StepListener listener = new StepListener(this);

        listener
//                .add(new Log())
                .add(new TakeScreenshot())
//                .add(new TakePageSource())
                .add(new CloseBrowsers())
                .add(new ClearTitles());

        if (TestRailAdapter.getInstance().isDefined()) {
            listener.add(new ReportTestRail());
        }

        Allure.LIFECYCLE.addListener(listener);
    }
}
