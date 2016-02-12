package com.qantium.uisteps.allure.tests.listeners;

import com.qantium.uisteps.allure.user.User;
import com.qantium.uisteps.core.properties.UIStepsProperty;
import com.qantium.uisteps.core.screenshots.Screenshot;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;

import java.io.IOException;

/**
 * Created by SolAn on 20.01.2016.
 */
public class TakeScreenshot extends UserFunction {


    public TakeScreenshot(User user) {
        super(user, UIStepsProperty.SCREENSHOTS_TAKE.toString());
    }

    @Override
    public boolean needsOn(Event event) {
        return super.needsOn(event) && getUser().getBrowserManager().hasAny();
    }


    @Override
    public Screenshot execute() {
        return attachScreenshot();
    }

    @Step("Attach screenshot META[listen=false]")
    protected Screenshot attachScreenshot() {
        Screenshot screenshot = getUser().inOpenedBrowser().getPhotographer().takeScreenshot();
        attachScreenshot(screenshot);
        return screenshot;
    }

    @Attachment(value = "screenshot")
    protected byte[] attachScreenshot(Screenshot screenshot) {

        try {
            return screenshot.asByteArray();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
