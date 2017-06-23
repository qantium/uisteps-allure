package com.qantium.uisteps.allure.tests.listeners.handlers;

import com.google.common.io.Files;
import com.qantium.uisteps.allure.tests.listeners.Event;
import com.qantium.uisteps.core.lifecycle.MetaInfo;
import com.qantium.uisteps.core.screenshots.Screenshot;
import ru.yandex.qatools.allure.model.Attachment;
import ru.yandex.qatools.allure.model.Step;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static com.qantium.uisteps.allure.properties.AllureUIStepsProperty.ALLURE_HOME_DIR;
import static com.qantium.uisteps.allure.tests.listeners.Meta.ATTACH_SCREENSHOT;
import static com.qantium.uisteps.allure.tests.listeners.Meta.LISTEN;
import static com.qantium.uisteps.core.properties.UIStepsProperty.SCREENSHOTS_TAKE;
import static com.qantium.uisteps.core.properties.UIStepsProperty.USER_DIR;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * Created by Anton Solyankin
 */
public class TakeScreenshot extends EventHandler {

    private Step lastStep;

    public TakeScreenshot() {
        super(SCREENSHOTS_TAKE);
    }

    @Override
    public boolean needsOn(Event event) {
        String listenMeta = "";
        String attachScreenShot = "";
        Step lastStep = getListener().getLastStep();

        if (lastStep != null && isNotEmpty(lastStep.getTitle())) {
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
    public Screenshot handle(Event event, Object... args) {
        lastStep = getListener().getLastStep();
        UUID uid = UUID.randomUUID();
        String dir = USER_DIR.getValue() + ALLURE_HOME_DIR.getValue();
        //TODO: inOpenedBrowser().getPhotographer
        Screenshot screenshot = getListener().getTest().getPhotographer().takeScreenshot();

        File file = new File(dir + "/screenshot-" + uid + ".png");

        try {
            Files.createParentDirs(file);
            Files.write(screenshot.asByteArray(), file);
        } catch (IOException ex) {
            throw new RuntimeException("Cannot save screenshot!", ex);
        }

        attach(file, "screenshot", "image/png");
        return screenshot;


    }

    private void attach(File file, String title, String type) {
        long size = file.length();
        Attachment attachment = new Attachment();
        attachment.withSource(file.getName())
                .withType(type)
                .withTitle(title)
                .withSize((int) size);

        lastStep.getAttachments().add(attachment);
    }

}
