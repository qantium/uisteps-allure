package com.qantium.uisteps.allure.tests;

import org.junit.runner.notification.Failure;
import ru.yandex.qatools.allure.junit.AllureRunListener;

/**
 * Created by SolAn on 15.01.2016.
 */
public class TestListener extends AllureRunListener {

    @Override
    public void testFailure(Failure failure) {
        super.testFailure(failure);
    }

    @Override
    public void testAssumptionFailure(Failure failure) {
        super.testAssumptionFailure(failure);
    }
}
