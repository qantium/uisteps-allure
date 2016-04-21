package com.qantium.uisteps.allure.browser;

import com.qantium.uisteps.allure.browser.factory.BrowserFactory;
import ru.yandex.qatools.allure.annotations.Step;
import com.qantium.uisteps.core.browser.Browser;

/**
 * Created by Anton Solyankin
 */
public class BrowserManager extends com.qantium.uisteps.core.browser.BrowserManager {

    public BrowserManager() {
        setBrowserFactory(new BrowserFactory());
    }

    @Step("Open {0} META[attach.screenshot=false][attach.source=false]")
    @Override
    public Browser open(Browser browser) {
        return super.open(browser);
    }

    @Step("Switch to browser by index {0} META[attach.screenshot=false][attach.source=false]")
    @Override
    public Browser switchToBrowserByIndex(int index) {
        return super.switchToBrowserByIndex(index);
    }

    @Step("Close current browser META[attach.screenshot=false][attach.source=false]")
    @Override
    public void closeCurrentBrowser() {
        super.closeCurrentBrowser();
    }

}
