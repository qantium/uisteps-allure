package com.qantium.uisteps.allure.browser;

import org.openqa.selenium.WebDriver;

/**
 * Created by SolAn on 13.01.2016.
 */
public class BrowserFactory extends com.qantium.uisteps.core.browser.BrowserFactory {

    @Override
    public Browser getBrowser(WebDriver driver) {
        setSettingsTo(driver);
        Browser browser = new Browser();
        browser.setDriver(driver);
        return browser;
    }
}
