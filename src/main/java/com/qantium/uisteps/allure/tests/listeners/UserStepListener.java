package com.qantium.uisteps.allure.tests.listeners;

import com.qantium.uisteps.allure.user.User;
import com.qantium.uisteps.core.properties.UIStepsProperties;
import com.qantium.uisteps.core.tests.StepMeta;
import org.apache.commons.lang3.StringUtils;
import ru.yandex.qatools.allure.Allure;
import ru.yandex.qatools.allure.events.*;
import ru.yandex.qatools.allure.experimental.LifecycleListener;
import ru.yandex.qatools.allure.model.Status;
import ru.yandex.qatools.allure.storages.StepStorage;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by Anton Solyankin
 */
public class UserStepListener extends LifecycleListener {

    private final User user;
    private StepStorage stepStorage;
    private ru.yandex.qatools.allure.model.Step lastStep;
    private final Set<UserFunction> functions = new HashSet();
    private Map<String, String> currentStepMetaParams;

    public UserStepListener(User user) {
        this.user = user;
        stepStorage = getStepStorage();
    }

    public User getUser() {
        return user;
    }

    public ru.yandex.qatools.allure.model.Step getLastStep() {
        return lastStep;
    }

    public UserStepListener add(UserFunction function) {
        functions.add(function);
        return this;
    }

    protected void execute(Event event) {
        if (!"false".equals(currentStepMetaParams.get("listen"))) {

            for (UserFunction function : functions) {

                if (function.needsOn(event)) {
                    function.execute();
                }
            }
        }
    }


    @Override
    public void fire(StepStartedEvent event) {
        currentStepMetaParams = new StepMeta(event.getTitle()).getMetaParams();
        execute(Event.STEP_STARTED);
        lastStep = stepStorage.getLast();

        String lastStepTitle = lastStep.getTitle();

        if (!StringUtils.isEmpty(lastStepTitle)) {
            lastStep.setTitle(new StepMeta(lastStepTitle).getStepTitleWithoutMeta());
        }
    }

    @Override
    public void fire(StepFinishedEvent event) {
        if (lastStep.getStatus() == Status.FAILED) {
            fireStepFailedEvent();
        } else {
            execute(Event.STEP_FINISHED);
        }
    }

    public void fireStepFailedEvent() {
        execute(Event.STEP_FAILED);
    }

    public StepStorage getStepStorage() {

        if (stepStorage == null) {

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
