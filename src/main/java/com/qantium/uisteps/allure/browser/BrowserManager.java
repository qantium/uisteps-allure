package com.qantium.uisteps.allure.browser;

import com.qantium.uisteps.allure.browser.factory.BrowserFactory;
import com.qantium.uisteps.core.browser.IBrowser;
import ru.yandex.qatools.allure.annotations.Step;

/**
 * Created by Anton Solyankin
 */
public class BrowserManager extends com.qantium.uisteps.core.browser.BrowserManager {

    public BrowserManager() {
        setBrowserFactory(new BrowserFactory());
    }

    @Step("Open {0} META[attach.screenshot=false][attach.source=false][attach.har=false]")
    @Override
    public IBrowser open(IBrowser browser) {
        return super.open(browser);
    }

    @Step("Switch to browser by index {0} META[attach.screenshot=false][attach.source=false][attach.har=false]")
    @Override
    public IBrowser switchToBrowserByIndex(int index) {
        return super.switchToBrowserByIndex(index);
    }

    @Step("Close current browser META[attach.screenshot=false][attach.source=false][attach.har=false]")
    @Override
    public void closeCurrentBrowser() {
        super.closeCurrentBrowser();
    }

}
