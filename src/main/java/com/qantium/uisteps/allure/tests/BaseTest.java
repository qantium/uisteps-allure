package com.qantium.uisteps.allure.tests;

import com.qantium.uisteps.allure.tests.listeners.StepListener;
import com.qantium.uisteps.allure.user.User;
import com.qantium.uisteps.core.screenshots.IPhotographer;
import com.qantium.uisteps.core.screenshots.Photographer;

/**
 * Created by Anton Solyankin
 */
public class BaseTest extends User {

    public BaseTest() {
        this(new StepListener());
    }

    public BaseTest(StepListener listener) {
        listener.set(this);
    }

    public IPhotographer getPhotographer() {
        return new Photographer(getDriver());
    }
}
