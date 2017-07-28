package com.qantium.uisteps.allure.tests.listeners.handlers;

import com.google.common.io.Files;
import com.qantium.uisteps.allure.tests.listeners.Event;
import com.qantium.uisteps.allure.tests.listeners.Meta;
import com.qantium.uisteps.core.lifecycle.MetaInfo;
import net.lightbody.bmp.core.har.Har;
import ru.yandex.qatools.allure.model.Attachment;
import ru.yandex.qatools.allure.model.Step;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static com.qantium.uisteps.allure.properties.AllureUIStepsProperty.ALLURE_HOME_DIR;
import static com.qantium.uisteps.core.properties.UIStepsProperty.USER_DIR;
import static com.qantium.uisteps.core.properties.UIStepsProperty.HAR_TAKE;
import static com.qantium.uisteps.core.properties.UIStepsProperty.WEBDRIVER_PROXY;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * Created by Solan on 20.07.2017.
 */
public class TakeHar extends EventHandler {

    private Step lastStep;

    public TakeHar() {
        super(HAR_TAKE);
    }

    @Override
    public boolean needsOn(Event event) {
        String listenMeta = "";
        String attachHar = "";
        Step lastStep = getListener().getLastStep();

        if (lastStep != null && !isEmpty(lastStep.getTitle())) {
            MetaInfo meta = new MetaInfo(lastStep.getTitle());
            listenMeta = meta.get(Meta.LISTEN.toString());
            attachHar = meta.get(Meta.ATTACH_HAR.toString());
        }
        return super.needsOn(event)
                && isNotEmpty(WEBDRIVER_PROXY.getValue())
                && (this.lastStep == null || !lastStep.equals(this.lastStep))
                && !"false".equals(listenMeta)
                && !"false".equals(attachHar)
                && getListener().getTest().hasAnyBrowser()
                && getListener().getTest().inOpenedBrowser().isAlive()
                && getListener().getTest().getCurrentBrowser().getProxy() != null;
    }


    @Override
    public Har handle(Event event, Object... args) {
        Har har = getListener().getTest().getCurrentBrowser().getProxy().getHar();

        lastStep = getListener().getLastStep();

        UUID uid = UUID.randomUUID();
        String dir = USER_DIR.getValue() + ALLURE_HOME_DIR.getValue();

        File file = new File(dir + "/har-" + uid + ".har");

        try {
            Files.createParentDirs(file);
            har.writeTo(file);
        } catch (IOException ex) {
            throw new RuntimeException("Cannot save har!", ex);
        }

        attach(file, "har", "text/plain");
        return har;
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