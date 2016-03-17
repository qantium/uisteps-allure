package com.qantium.uisteps.allure.tests.listeners.functions;

import com.qantium.uisteps.allure.tests.listeners.Event;

/**
 * Created by Anton Solyankin
 */
public class CloseBrowsers extends ListenerFunction {

    public CloseBrowsers() {
        super(new Event[]{Event.TEST_FINISHED});
    }

    @Override
    public Object execute() {
        closeBrowsers();
        return null;
    }

    private void closeBrowsers() {
        getListener().getTest().closeAllBrowsers();
    }
}
