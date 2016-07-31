package com.qantium.uisteps.allure.tests.listeners.handlers;

import com.qantium.uisteps.allure.tests.listeners.Event;
import com.qantium.uisteps.core.lifecycle.MetaInfo;
import com.qantium.uisteps.core.screenshots.Screenshot;
import ru.yandex.qatools.allure.model.Attachment;
import ru.yandex.qatools.allure.model.Step;

import java.util.List;
import java.util.UUID;

import static com.qantium.uisteps.allure.properties.AllureUIStepsProperty.ALLURE_HOME_DIR;
import static com.qantium.uisteps.allure.tests.listeners.Meta.ATTACH_SCREENSHOT;
import static com.qantium.uisteps.allure.tests.listeners.Meta.LISTEN;
import static com.qantium.uisteps.core.properties.UIStepsProperties.getProperty;
import static com.qantium.uisteps.core.properties.UIStepsProperty.SCREENSHOTS_TAKE;
import static com.qantium.uisteps.core.properties.UIStepsProperty.USER_DIR;
import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * Created by Anton Solyankin
 */
public class TakeScreenshot extends EventHandler {

    private Step lastStep;

    public TakeScreenshot() {
        super(SCREENSHOTS_TAKE);
    }

    public boolean needsOn(Event event) {
        String listenMeta = "";
        String attachScreenShot = "";
        Step lastStep = getListener().getLastStep();

        if (lastStep != null && !isEmpty(lastStep.getTitle())) {
            MetaInfo meta = new MetaInfo(lastStep.getTitle());
            listenMeta = meta.get(LISTEN.toString());
            attachScreenShot = meta.get(ATTACH_SCREENSHOT.toString());
        }
        return super.needsOn(event)
                && (this.lastStep == null || !lastStep.equals(this.lastStep))
                && !"false".equals(listenMeta)
                && !"false".equals(attachScreenShot)
                && getListener().getTest().hasAnyBrowser()
                && getListener().getTest().inOpenedBrowser().isAlive();
    }

    @Override
    public Screenshot handle(Event event) {
        lastStep = getListener().getLastStep();
        UUID uid = UUID.randomUUID();
        String dir = getProperty(USER_DIR) + getProperty(ALLURE_HOME_DIR);
        //TODO: inOpenedBrowser().getPhotographer
        Screenshot screenshot = getListener().getTest().getPhotographer().takeScreenshot();
        long size = screenshot.toDir(dir).save("screenshot-" + uid + ".png").length();
        Attachment attachment = new Attachment();
        attachment.withSource("screenshot-" + uid + ".png")
                .withType("image/png")
                .withTitle("screenshot")
                .withSize((int) size);
        List<Attachment> attachments = lastStep.getAttachments();
        attachments.add(attachment);
        return screenshot;
    }
}
