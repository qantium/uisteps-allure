package com.qantium.uisteps.allure.tests;

import com.qantium.uisteps.allure.tests.listeners.TakePageSource;
import com.qantium.uisteps.allure.tests.listeners.TakeScreenshot;
import com.qantium.uisteps.allure.tests.listeners.UserStepListener;
import com.qantium.uisteps.allure.user.User;
import ru.yandex.qatools.allure.Allure;

/**
 * Created by Anton Solyankin
 */
public class BaseUserAllureTest<U extends User> extends BaseAllureTest {

    public final U user;

    public BaseUserAllureTest(U user) {
        this.user = user;
        UserStepListener listener = new UserStepListener(user);

        TakeScreenshot takeScreenshot = new TakeScreenshot(user);
        TakePageSource takePageSource = new TakePageSource(user);


        listener
                .add(takeScreenshot, takeScreenshot.getProperty())
                .add(takePageSource, takePageSource.getProperty());

        Allure.LIFECYCLE.addListener(listener);
    }
}
