package com.qantium.uisteps.allure.tests.listeners.functions;

import com.qantium.uisteps.allure.tests.BaseTest;
import com.qantium.uisteps.allure.tests.listeners.Meta;
import com.qantium.uisteps.core.browser.AlertException;
import com.qantium.uisteps.core.lifecycle.MetaInfo;
import com.qantium.uisteps.core.properties.UIStepsProperty;
import com.qantium.uisteps.core.screenshots.Screenshot;
import com.qantium.uisteps.allure.tests.listeners.Event;

import java.awt.*;

/**
 * Created by Anton Solyankin
 */
public class TakeScreenshot extends ListenerFunction {


    public TakeScreenshot() {
        super(UIStepsProperty.SCREENSHOTS_TAKE.toString());
    }

    public boolean needsOn(Event event) {
        String listenMeta = "";
        String attachScreenShot = "";
        ru.yandex.qatools.allure.model.Step lastStep = getListener().getLastStep();
        if (lastStep != null && lastStep.getTitle() != null) {
            MetaInfo meta = new MetaInfo(lastStep.getTitle());
            listenMeta = meta.get(Meta.LISTEN.toString());
            attachScreenShot = meta.get(Meta.ATTACH_SCREENSHOT.toString());
        }
        return super.needsOn(event)
                && !"false".equals(listenMeta)
                && !"false".equals(attachScreenShot)
                && getListener().getTest().getBrowserManager().hasAny()
                && getListener().getTest().inOpenedBrowser().isAlive();
    }

    @Override
    public Screenshot execute() {
        BaseTest test = getListener().getTest();
        try {
            return test.takeScreenshot();
        } catch (AlertException ex) {

            try {
                Screenshot screenshot = new Screenshot(new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize())));
                test.getStorage().attach("screenshot", screenshot.asByteArray());
            } catch (AWTException e) {
                throw new AlertException("Cannot get screen shot even by robot!", ex);
            }

            throw ex;
        }
    }
}
