package com.qantium.uisteps.allure.tests;

import com.qantium.uisteps.allure.storage.Storage;
import com.qantium.uisteps.allure.tests.listeners.TakePageSource;
import com.qantium.uisteps.allure.tests.listeners.TakeScreenshot;
import com.qantium.uisteps.allure.tests.listeners.UserStepListener;
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
        UserStepListener listener = new UserStepListener(this.user);

        TakeScreenshot takeScreenshot = new TakeScreenshot(this.user);
        TakePageSource takePageSource = new TakePageSource(this.user);

        listener.add(takeScreenshot).add(takePageSource);

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
