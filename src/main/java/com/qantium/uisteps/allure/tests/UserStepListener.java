package com.qantium.uisteps.allure.tests;

import com.qantium.uisteps.allure.user.User;
import com.qantium.uisteps.core.screenshots.Screenshot;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;
import ru.yandex.qatools.allure.events.StepFinishedEvent;
import ru.yandex.qatools.allure.events.StepStartedEvent;
import ru.yandex.qatools.allure.experimental.LifecycleListener;

import java.io.IOException;

/**
 * Created by Anton Solyankin
 */
public class UserStepListener<U extends User> extends LifecycleListener {

    private final U user;
    private String currentStepName;
    private String currentStepTitle;

    public UserStepListener(U user) {
        this.user = user;
    }

    @Override
    public void fire(StepStartedEvent event) {
        currentStepName = event.getName();
        currentStepTitle = event.getTitle();
    }

    @Override
    public void fire(StepFinishedEvent event) {
        if (needsToTakeScreenshot()) {
            $attachInfo();
        }
    }

    private boolean needsToTakeScreenshot() {
        return user.getBrowserManager().hasAny() && !"attachedInfo".equals(currentStepName);
    }

    private boolean needsToTakeSource() {
        return user.getBrowserManager().hasAny() && !"$attachInfo".equals(currentStepName);
    }

    @Step("attached info")
    public void $attachInfo() {
        try {
            attachScreenshot(user.inOpenedBrowser().getPhotographer().takeScreenshot());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }


    @Attachment(value = "screenshot")
    protected byte[] attachScreenshot(Screenshot screenshot) throws IOException {
        return screenshot.asByteArray();
    }

}
