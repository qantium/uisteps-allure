package com.qantium.uisteps.allure.tests;

import com.qantium.uisteps.allure.user.User;
import com.qantium.uisteps.core.properties.UIStepsProperties;
import com.qantium.uisteps.core.properties.UIStepsProperty;
import com.qantium.uisteps.core.screenshots.Screenshot;
import com.qantium.uisteps.core.tests.StepMeta;
import org.apache.commons.lang3.StringUtils;
import ru.yandex.qatools.allure.Allure;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;
import ru.yandex.qatools.allure.events.StepFinishedEvent;
import ru.yandex.qatools.allure.events.StepStartedEvent;
import ru.yandex.qatools.allure.experimental.LifecycleListener;
import ru.yandex.qatools.allure.model.Status;
import ru.yandex.qatools.allure.storages.StepStorage;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Anton Solyankin
 */
public class UserStepListener<U extends User> extends LifecycleListener {

    private U user;
    private Map<String, String> currentStepMetaParams;
    private StepStorage stepStorage;
    private ru.yandex.qatools.allure.model.Step lastStep;

    private final static String BEFORE_AND_AFTER_EACH_STEP = "BEFORE_AND_AFTER_EACH_STEP";
    private final static String BEFORE_AND_AFTER_EACH_ACTION = "BEFORE_AND_AFTER_EACH_ACTION";
    private final static String BEFORE_EACH_STEP = "BEFORE_EACH_STEP";
    private final static String BEFORE_EACH_ACTION = "BEFORE_EACH_ACTION";
    private final static String AFTER_EACH_STEP = "AFTER_EACH_STEP";
    private final static String AFTER_EACH_ACTION = "AFTER_EACH_ACTION";


    public UserStepListener(U user) {
        this.user = user;
        stepStorage = getStepStorage();

    }

    public void setUser(U user) {
        this.user = user;
    }

    public U getUser() {
        return user;
    }

    public Map<String, String> getCurrentStepMetaParams() {
        return currentStepMetaParams;
    }

    public ru.yandex.qatools.allure.model.Step getLastStep() {
        return lastStep;
    }


    @Override
    public void fire(StepStartedEvent event) {
        lastStep = stepStorage.getLast();
        currentStepMetaParams = new StepMeta(event.getTitle()).getMetaParams();
        attachInfo(Phase.STEP_STARTED);

        String lastStepTitle = lastStep.getTitle();

        if(!StringUtils.isEmpty(lastStepTitle)) {
            lastStep.setTitle(new StepMeta(lastStepTitle).getStepTitleWithoutMeta());
        }
    }



    @Override
    public void fire(StepFinishedEvent event) {

        Phase phase = Phase.STEP_FINISHED;

        if(lastStep.getStatus() == Status.FAILED) {
            phase = Phase.STEP_FAILED;
        }
        attachInfo(phase);
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

        if(!needOn(phase, getProprety(attach))) {
            return false;
        }

        if ("false".equals((currentStepMetaParams.get("attach." + attach)))) {
            return false;
        }

        if ("false".equals((currentStepMetaParams.get("attach.info")))) {
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

    protected String getProprety(Attach attach) {

        switch (attach) {
            case SCREENSHOT:
                return UIStepsProperties.getProperty(UIStepsProperty.SCREENSHOTS_TAKE);
            case SOURCE:
                return UIStepsProperties.getProperty(UIStepsProperty.SOURCE_TAKE);
            default:
                return null;
        }
    }

    protected boolean needOn(Phase phase, String property) {

        if(phase == Phase.STEP_FAILED) {
            return true;
        }

        boolean isAction = "action".equals((currentStepMetaParams.get("type")));
        boolean forAction = BEFORE_AND_AFTER_EACH_ACTION.equals(property) || BEFORE_EACH_ACTION.equals(property) || AFTER_EACH_ACTION.equals(property);
        boolean beforeAndAfter = BEFORE_AND_AFTER_EACH_STEP.equals(property) || BEFORE_AND_AFTER_EACH_ACTION.equals(property);
        boolean before = BEFORE_EACH_STEP.equals(property) || BEFORE_EACH_ACTION.equals(property);
        boolean after = AFTER_EACH_STEP.equals(property) || AFTER_EACH_ACTION.equals(property);


        if(phase == Phase.STEP_STARTED) {
            if(before || beforeAndAfter) {

                if(!forAction) {
                    return !isAction;
                }
                return true;

            }
        }

        if(phase == Phase.STEP_FINISHED) {
            if(after || beforeAndAfter) {
                if(!forAction) {
                    return !isAction;
                } else {
                    return true;
                }
            }
        }

        return false;
    }


    protected void attach(Attach attach) throws IOException {

        switch (attach) {
            case SCREENSHOT: {
                attachScreenshot(user.inOpenedBrowser().getPhotographer().takeScreenshot());
                break;
            }

            case SOURCE: {
                attachPageSource(user.inOpenedBrowser().getDriver().getPageSource());
                break;
            }
        }
    }

    @Step("Attach {0} META[attach.info=false]")
    public void attach(Set<Attach> attach) {
        try {
            for(Attach att: attach) {
                attach(att);
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
        STEP_STARTED, STEP_FINISHED, STEP_FAILED;
    }

    public enum Attach {

        SCREENSHOT, SOURCE;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    public StepStorage getStepStorage() {

        if(stepStorage == null) {

            try {
                Field stepStorageField = Allure.class.getDeclaredField("stepStorage");
                stepStorageField.setAccessible(true);
                stepStorage = (StepStorage) stepStorageField.get(Allure.LIFECYCLE);
            } catch (NoSuchFieldException | IllegalAccessException ex) {
                throw new RuntimeException("Cannot get stepStorage!\nCause: " + ex);
            }
        }
        return stepStorage;
    }
}
