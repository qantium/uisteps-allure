package com.qantium.uisteps.allure.tests.listeners.functions;

import com.qantium.uisteps.allure.browser.BrowserManager;
import com.qantium.uisteps.allure.tests.listeners.Event;
import com.qantium.uisteps.core.properties.UIStepsProperty;
import com.qantium.uisteps.core.screenshots.Screenshot;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;


/**
 * Created by Anton Solyankin
 */
public class TakeScreenshot extends ListenerFunction {


    public TakeScreenshot() {
        super(UIStepsProperty.SCREENSHOTS_TAKE.toString());
    }

    @Override
    public boolean needsOn(Event event) {
        return super.needsOn(event) && getBrowserManager().hasAny();
    }

    @Override
    public Screenshot execute() {
        return attachScreenshot();
    }

    @Step("Attach screenshot META[listen=false]")
    protected Screenshot attachScreenshot() {
        Screenshot screenshot = getBrowserManager().getCurrentBrowser().getPhotographer().takeScreenshot();
        attachScreenshot(screenshot);
        return screenshot;
    }

    @Attachment(value = "screenshot")
    protected byte[] attachScreenshot(Screenshot screenshot) {
        return screenshot.asByteArray();
    }

    private BrowserManager getBrowserManager() {
        return new BrowserManager();
    }
}
