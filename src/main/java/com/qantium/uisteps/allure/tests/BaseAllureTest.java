package com.qantium.uisteps.allure.tests;

import com.qantium.uisteps.allure.storage.Storage;
import com.qantium.uisteps.allure.verify.Assume;
import com.qantium.uisteps.allure.verify.Verify;
import com.qantium.uisteps.core.tests.BaseTest;
/**
 * Created by Anton Solyankin
 */
public class BaseAllureTest extends BaseTest {

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
