package com.qantium.uisteps.allure.tests;

import com.qantium.uisteps.allure.assertions.HardAssert;
import com.qantium.uisteps.allure.assertions.SoftAssert;
import com.qantium.uisteps.allure.tests.listeners.StepListener;
import com.qantium.uisteps.allure.user.User;
import com.qantium.uisteps.core.screenshots.IPhotographer;
import com.qantium.uisteps.core.screenshots.Photographer;

import java.util.UUID;

/**
 * Created by Anton Solyankin
 */
public class BaseTest extends User {

    private final SoftAssert softAssertion;
    private final HardAssert hardAssertion;

    public BaseTest() {
        this(new StepListener());
    }

    public BaseTest(StepListener listener) {
        listener.set(this);
        softAssertion = new SoftAssert(listener);
        hardAssertion = new HardAssert(listener);

    }

    public IPhotographer getPhotographer() {
        return new Photographer(getDriver());
    }

    public SoftAssert softAssert() {
        return softAssertion;
    }

    public HardAssert hardAssert() {
        return hardAssertion;
    }
}
