package com.qantium.uisteps.allure.tests.listeners.handlers;

import com.google.common.io.Files;
import com.qantium.uisteps.allure.tests.listeners.Event;
import ru.yandex.qatools.allure.model.Attachment;
import static ru.yandex.qatools.allure.model.Status.*;
import ru.yandex.qatools.allure.model.Step;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.qantium.uisteps.allure.properties.AllureUIStepsProperty.ALLURE_HOME_DIR;
import static com.qantium.uisteps.allure.tests.listeners.Event.STEP_FAILED;
import static com.qantium.uisteps.core.properties.UIStepsProperties.getProperty;
import static com.qantium.uisteps.core.properties.UIStepsProperty.USER_DIR;

/**
 * Created by Anton Solyankin
 */
public class CatchErrors extends EventHandler {

    private Step lastStep;

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

        File file = new File(System.getProperty("user.dir") + getProperty(ALLURE_HOME_DIR) + "/error_message-" + uid + ".txt");

        try {
            Files.createParentDirs(file);
            Files.write(error.getMessage().getBytes(), file);
        } catch (IOException ex) {
            throw new RuntimeException("Cannot save error!", ex);
        }
        long size = file.length();

        Attachment errorMessage = new Attachment();
        errorMessage.withSource("error_message-" + uid + ".txt")
                .withType("text/plain")
                .withTitle("error message")
                .withSize((int) size);

        attachments.add(errorMessage);

        Throwable cause = error.getCause();
        if (cause != null) {

            file = new File(getProperty(USER_DIR) + getProperty(ALLURE_HOME_DIR) + "/error_cause-" + uid + ".txt");

            try {
                Files.createParentDirs(file);
                StackTraceElement[] stackTrace = cause.getStackTrace();

                for (StackTraceElement stackTraceElement : stackTrace) {
                    Files.write(stackTraceElement.toString().getBytes(), file);
                }
            } catch (IOException ex) {
                throw new RuntimeException("Cannot save error!", ex);
            }
            size = file.length();

            Attachment errorCause = new Attachment();
            errorCause.withSource("error_cause-" + uid + ".txt")
                    .withType("text/plain")
                    .withTitle("error cause")
                    .withSize((int) size);


            attachments.add(errorCause);
        }
        return error;
    }
}
