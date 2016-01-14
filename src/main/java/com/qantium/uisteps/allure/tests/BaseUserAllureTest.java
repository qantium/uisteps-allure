package com.qantium.uisteps.allure.tests;

import com.qantium.uisteps.allure.user.User;
import ru.yandex.qatools.allure.Allure;

/**
 * Created by Anton Solyankin
 */
public class BaseUserAllureTest<U extends User> extends BaseAllureTest {

    public final U user;

    public BaseUserAllureTest(U user) {
        this.user = user;
        Allure.LIFECYCLE.addListener(new UserStepListener(user));
    }
}
