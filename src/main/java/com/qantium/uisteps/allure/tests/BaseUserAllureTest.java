package com.qantium.uisteps.allure.tests;

import com.qantium.uisteps.allure.storage.Storage;
import com.qantium.uisteps.allure.tests.listeners.functions.ClearTitles;
import com.qantium.uisteps.allure.tests.listeners.functions.ReportTestRail;
import com.qantium.uisteps.allure.tests.listeners.functions.TakePageSource;
import com.qantium.uisteps.allure.tests.listeners.functions.TakeScreenshot;
import com.qantium.uisteps.allure.tests.listeners.StepListener;
import com.qantium.uisteps.allure.user.User;
import com.qantium.uisteps.allure.verify.Assume;
import com.qantium.uisteps.allure.verify.Verify;
import com.qantium.uisteps.core.tests.BaseUserTest;
import ru.yandex.qatools.allure.Allure;

/**
 * Created by Anton Solyankin
 */
public class BaseUserAllureTest<U extends User> extends BaseUserTest<U> {

    public BaseUserAllureTest(Class<U> user) {
        super(user);
        initListeners();
    }

    protected void initListeners() {
        StepListener listener = new StepListener();

        listener
                .add(new TakeScreenshot())
                .add(new TakePageSource())
                .add(new ReportTestRail())
                .add(new ClearTitles());

        Allure.LIFECYCLE.addListener(listener);
    }

    @Override
    protected Verify getAssertions() {
        return new Verify();
    }

    @Override
    protected Assume getAssumtions() {
        return new Assume();
    }

    @Override
    protected Storage getStorage() {
        return new Storage();
    }
}
