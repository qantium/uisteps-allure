package com.qantium.uisteps.allure.tests.listeners.testng;

/**
 * Created by Solan on 25.01.2016.
 */

import com.qantium.uisteps.core.utils.testrail.TestRailAdapter;
import com.qantium.uisteps.core.utils.testrail.TestRailStatus;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class UIStepsTestNGListener  extends TestListenerAdapter {


    @Override
    public void onTestSuccess(ITestResult testResult) {
        reportTestrail(testResult);

    }

    @Override
    public void onTestFailure(ITestResult testResult) {
        reportTestrail(testResult);
    }

    @Override
    public void onTestSkipped(ITestResult testResult) {
        reportTestrail(testResult);
    }

    protected void reportTestrail(ITestResult testResult) {

        TestRailStatus status = TestRailStatus.PASSED;

        switch (testResult.getStatus()) {
            case 2:
                status = TestRailStatus.FAILED;
                break;
        }

        String[] groups = testResult.getMethod().getGroups();

        TestRailAdapter testRailAdapter = new TestRailAdapter();

        for(String group: groups) {

            if (group.startsWith("#")) {
                testRailAdapter.addResult(group.replace("#", ""), status);
            }
        }

    }
}
