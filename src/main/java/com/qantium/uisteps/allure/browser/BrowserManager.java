package com.qantium.uisteps.allure.browser;

import com.qantium.uisteps.allure.browser.factory.BrowserFactory;
import ru.yandex.qatools.allure.annotations.Step;

/**
 * Created by Anton Solyankin
 */
public class BrowserManager extends com.qantium.uisteps.core.browser.BrowserManager {

    public BrowserManager() {
        setBrowserFactory(new BrowserFactory());
    }

    @Step("Open {0} META[listen=false]")
    @Override
    public com.qantium.uisteps.core.browser.Browser open(com.qantium.uisteps.core.browser.Browser browser) {
        return super.open(browser);
    }

    @Step("Switch to browser by index {0} META[listen=false]")
    @Override
    public com.qantium.uisteps.core.browser.Browser switchToBrowserByIndex(int index) {
        return super.switchToBrowserByIndex(index);
    }

    @Step("Close current browser META[listen=false]")
    @Override
    public void closeCurrentBrowser() {
        super.closeCurrentBrowser();
    }

}
