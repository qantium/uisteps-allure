package com.qantium.uisteps.allure.tests.listeners.handlers;

import com.google.common.io.Files;
import com.qantium.uisteps.allure.tests.listeners.Event;
import com.qantium.uisteps.allure.tests.listeners.Meta;
import com.qantium.uisteps.core.lifecycle.MetaInfo;
import ru.yandex.qatools.allure.model.Attachment;
import ru.yandex.qatools.allure.model.Step;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static com.qantium.uisteps.allure.properties.AllureUIStepsProperty.ALLURE_HOME_DIR;
import static com.qantium.uisteps.core.properties.UIStepsProperties.*;
import static com.qantium.uisteps.core.properties.UIStepsProperty.*;
import static org.apache.commons.lang3.StringUtils.isEmpty;


/**
 * Created by Anton Solyankin
 */
public class TakePageSource extends EventHandler {

    private Step lastStep;

    public TakePageSource() {
        super(SOURCE_TAKE.toString());
    }

    public boolean needsOn(Event event) {
        String listenMeta = "";
        String attachSource = "";
        Step lastStep = getListener().getLastStep();
        if (lastStep != null && !isEmpty(lastStep.getTitle())) {
            MetaInfo meta = new MetaInfo(lastStep.getTitle());
            listenMeta = meta.get(Meta.LISTEN.toString());
            attachSource = meta.get(Meta.ATTACH_SOURCE.toString());
        }
        return super.needsOn(event)
                && (this.lastStep == null || !lastStep.equals(this.lastStep))
                && !"false".equals(listenMeta)
                && !"false".equals(attachSource)
                && getListener().getTest().getBrowserManager().hasAny()
                && getListener().getTest().inOpenedBrowser().isAlive();
    }

    @Override
    public String handle(Event event) {
        lastStep = getListener().getLastStep();
        return attachPageSource();
    }

    protected String attachPageSource() {

        UUID uid = UUID.randomUUID();
        String pageSource = getListener().getTest().inOpenedBrowser().getDriver().getPageSource();
        String dir = getProperty(USER_DIR) + getProperty(ALLURE_HOME_DIR);
        File file = new File(dir + "/page_source-" + uid + ".html");

        try {
            Files.createParentDirs(file);
            Files.write(pageSource.getBytes(), file);
        } catch (IOException ex) {
            throw new RuntimeException("Cannot save page source!", ex);
        }

        attach(file, "page html", "text/html", uid);
        attach(file, "page source", "text/plain", uid);

        return pageSource;
    }

    private void attach(File file, String title, String type, UUID uid) {
        long size = file.length();
        Attachment attachment = new Attachment();
        attachment.withSource("page_source-" + uid + ".html")
                .withType(type)
                .withTitle(title)
                .withSize((int) size);

        lastStep.getAttachments().add(attachment);

    }

}
