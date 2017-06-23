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
import static com.qantium.uisteps.core.properties.UIStepsProperty.SOURCE_TAKE;
import static com.qantium.uisteps.core.properties.UIStepsProperty.USER_DIR;
import static org.apache.commons.lang3.StringUtils.isEmpty;


/**
 * Created by Anton Solyankin
 */
public class TakePageSource extends EventHandler {

    private Step lastStep;

    public TakePageSource() {
        super(SOURCE_TAKE);
    }

    @Override
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
                && getListener().getTest().hasAnyBrowser()
                && getListener().getTest().inOpenedBrowser().isAlive();
    }

    @Override
    public String handle(Event event, Object... args) {
        lastStep = getListener().getLastStep();
        return attachPageSource();
    }

    protected String attachPageSource() {

        UUID uid = UUID.randomUUID();
        String pageSource = getListener().getTest().inOpenedBrowser().getDriver().getPageSource();
        String dir = USER_DIR.getValue() + ALLURE_HOME_DIR.getValue();
        File fileHtml = new File(dir + "/page_html-" + uid + ".html");
        File fileTxt = new File(dir + "/page_source-" + uid + ".txt");

        try {
            byte[] bytes = pageSource.getBytes();

            Files.createParentDirs(fileHtml);
            Files.write(bytes, fileHtml);
            Files.write(bytes, fileTxt);
        } catch (IOException ex) {
            throw new RuntimeException("Cannot save page html!", ex);
        }

        attach(fileHtml, "page html", "text/html");
        attach(fileTxt, "page source", "text/plain");

        return pageSource;
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
