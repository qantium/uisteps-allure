package com.qantium.uisteps.allure.browser.factory;

import com.qantium.uisteps.allure.browser.Browser;
import org.openqa.selenium.WebDriver;

/**
 * Created by Anton Solyankin
 */
public class BrowserFactory extends com.qantium.uisteps.core.browser.factory.BrowserFactory {

    @Override
    public com.qantium.uisteps.core.browser.Browser getBrowser(WebDriver driver) {
        Browser browser = new Browser();
        browser.setDriver(driver);
        return browser;
    }
}
