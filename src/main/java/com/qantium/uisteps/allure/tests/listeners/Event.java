package com.qantium.uisteps.allure.tests.listeners;

/**
 * Created by Anton Solyankin
 */
public enum Event {
    STEP_STARTED,
    STEP_FINISHED,
    STEP_FAILED,
    TEST_STARTED,
    TEST_FINISHED,
    SUITE_STARTED,
    SUITE_FINISHED,
    ASSERT,
    ASSERT_PASSED,
    ASSERT_BROKEN;

    @Override
    public String toString() {
        return name().replace("_", " ");
    }
}
