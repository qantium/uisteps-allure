package com.qantium.uisteps.allure.browser;

import ru.yandex.qatools.allure.annotations.Step;

/**
 * Created by Anton Solyankin
 */
public class BrowserManager extends com.qantium.uisteps.core.browser.BrowserManager {

    public BrowserManager() {
        setBrowserFactory(new BrowserFactory());
    }

    @Step("Open {0}")
    @Override
    public com.qantium.uisteps.core.browser.Browser open(com.qantium.uisteps.core.browser.Browser browser) {
        return super.open(browser);
    }

    @Step
    @Override
    public com.qantium.uisteps.core.browser.Browser switchToBrowserByIndex(int index) {
        return super.switchToBrowserByIndex(index);
    }

    @Step
    @Override
    public void closeCurrentBrowser() {
        super.closeCurrentBrowser();
    }

    @Step
    @Override
    public void closeAllBrowsers() {
        super.closeAllBrowsers();
    }

}
