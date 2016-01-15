package com.qantium.uisteps.allure.tests;

import com.qantium.uisteps.allure.user.User;
import com.qantium.uisteps.core.properties.UIStepsProperties;
import com.qantium.uisteps.core.properties.UIStepsProperty;
import com.qantium.uisteps.core.screenshots.Screenshot;
import com.qantium.uisteps.core.tests.StepMeta;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;
import ru.yandex.qatools.allure.events.StepFinishedEvent;
import ru.yandex.qatools.allure.events.StepStartedEvent;
import ru.yandex.qatools.allure.experimental.LifecycleListener;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Anton Solyankin
 */
public class UserStepListener<U extends User> extends LifecycleListener {

    private final U user;
    private String currentStepName;
    private String currentStepTitle;
    private Map<String, String> currentStepMetaParams;

    private final static String FOR_EACH_ACTION = "FOR_EACH_ACTION";
    private final static String BEFORE_AND_AFTER_EACH_STEP = "BEFORE_AND_AFTER_EACH_STEP";
    private final static String AFTER_EACH_STEP = "AFTER_EACH_STEP";
    private final static String FOR_FAILURES = "FOR_FAILURES";

    private final String takeScreenshot;
    private final String takeSource;

    public UserStepListener(U user) {
        this.user = user;
        takeScreenshot = UIStepsProperties.getProperty(UIStepsProperty.SCREENSHOTS_TAKE);
        takeSource = UIStepsProperties.getProperty(UIStepsProperty.SOURCE_TAKE);
    }

    @Override
    public void fire(StepStartedEvent event) {
        currentStepName = event.getName();
        currentStepTitle = event.getTitle();
        currentStepMetaParams = new StepMeta(currentStepTitle).getMetaParams();
        attachInfo(Phase.STEP_STARTED);
    }

    @Override
    public void fire(StepFinishedEvent event) {
        attachInfo(Phase.STEP_FINISHED);
    }

    protected void attachInfo(Phase phase) {

        Set<Attach> attach = new HashSet();

        if (need(Attach.SCREENSHOT, phase)) {
            attach.add(Attach.SCREENSHOT);
        }

        if (need(Attach.SOURCE, phase)) {
            attach.add(Attach.SOURCE);
        }

        if(!attach.isEmpty()) {
            attach(attach);
        }
    }

    protected boolean need(Attach attach, Phase phase) {

        if (!needOn(phase) || "false".equals((currentStepMetaParams.get(attach.toString())))){
            return false;
        }

        if (needBrowserFor(attach)) {
            return user.getBrowserManager().hasAny();
        }
        return true;
    }

    protected boolean needBrowserFor(Attach attach) {

        switch (attach) {
            case SCREENSHOT:
                return true;
            case SOURCE:
                return true;
            default:
                return false;
        }
    }

    protected boolean needOn(Phase phase) {
        switch (phase) {
            case STEP_STARTED:
                return BEFORE_AND_AFTER_EACH_STEP.equals(takeScreenshot);
            case STEP_FINISHED:
                return BEFORE_AND_AFTER_EACH_STEP.equals(takeSource)
                        || AFTER_EACH_STEP.equals(takeSource)
                        || FOR_EACH_ACTION.equals(takeSource);
            default:
                return false;
        }
    }


    @Step("{0} META[attach.screen=false][attach.source=false]")
    public void attach(Set<Attach> attach) {
        try {
            if (attach.contains(Attach.SCREENSHOT)) {
                attachScreenshot(user.inOpenedBrowser().getPhotographer().takeScreenshot());
            }

            if (attach.contains(Attach.SOURCE)) {
                attachPageSource(user.inOpenedBrowser().getDriver().getPageSource());
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Attachment(value = "screenshot")
    protected byte[] attachScreenshot(Screenshot screenshot) throws IOException {
        return screenshot.asByteArray();
    }

    @Attachment(value = "page source", type = "text/plain")
    protected String attachPageSource(String pageSource) throws IOException {
        return pageSource;
    }

    public enum Phase {
        STEP_STARTED, STEP_FINISHED;
    }

    public enum Attach {

        SCREENSHOT {
            @Override
            public String toString() {
                return "attach.screen";
            }
        },

        SOURCE {
            @Override
            public String toString() {
                return "attach.source";
            }
        }
    }
}
