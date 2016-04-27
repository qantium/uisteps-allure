package com.qantium.uisteps.allure.tests.listeners.handlers;

import com.google.common.io.Files;
import com.qantium.uisteps.allure.tests.listeners.Event;
import ru.yandex.qatools.allure.model.Attachment;
import ru.yandex.qatools.allure.model.Step;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;
import java.util.UUID;

import static com.qantium.uisteps.allure.properties.AllureUIStepsProperty.ALLURE_HOME_DIR;
import static com.qantium.uisteps.allure.tests.listeners.Event.STEP_FAILED;
import static com.qantium.uisteps.core.properties.UIStepsProperties.getProperty;
import static com.qantium.uisteps.core.properties.UIStepsProperty.USER_DIR;
import static ru.yandex.qatools.allure.model.Status.BROKEN;
import static ru.yandex.qatools.allure.model.Status.FAILED;

/**
 * Created by Anton Solyankin
 */
public class CatchErrors extends EventHandler {

    private Step lastStep;
    private String dir = getProperty(USER_DIR) + getProperty(ALLURE_HOME_DIR);
    private Charset UTF_8 = Charset.forName("UTF-8");

    public CatchErrors() {
        super(new Event[]{STEP_FAILED});
    }

    public boolean needsOn(Event event) {
        Step lastStep = getListener().getLastStep();
        return super.needsOn(event) && (this.lastStep == null || !lastStep.equals(this.lastStep));
    }

    @Override
    public Throwable handle(Event event) {
        lastStep = getListener().getLastStep();
        List<Attachment> attachments = lastStep.getAttachments();

        UUID uid = UUID.randomUUID();
        Throwable error = getListener().getError();

        if (error instanceof AssertionError) {
            lastStep.setStatus(FAILED);
        } else {
            lastStep.setStatus(BROKEN);
        }

        File file = new File(dir, "/error_message-" + uid + ".txt");

        try {
            Files.createParentDirs(file);
            Files.write(error.getMessage().getBytes(), file);
        } catch (Exception ex) {
            throw new RuntimeException("Cannot save error!", ex);
        }
        long size = file.length();

        Attachment errorMessage = new Attachment();
        errorMessage.withSource("error_message-" + uid + ".txt")
                .withType("text/plain")
                .withTitle("error message")
                .withSize((int) size);

        attachments.add(errorMessage);

        file = new File(dir, "/error_stacktrace-" + uid + ".txt");

        try {
            Files.createParentDirs(file);
            StackTraceElement[] stackTrace = error.getStackTrace();


            for (StackTraceElement stackTraceElement : stackTrace) {
                Files.append(stackTraceElement + "\n", file, UTF_8);
            }
        } catch (Exception ex) {
            throw new RuntimeException("Cannot save stacktrace!", ex);
        }
        size = file.length();

        Attachment stacktrace = new Attachment();
        stacktrace.withSource("error_stacktrace-" + uid + ".txt")
                .withType("text/plain")
                .withTitle("error stacktrace")
                .withSize((int) size);


        attachments.add(stacktrace);

        return error;
    }
}
