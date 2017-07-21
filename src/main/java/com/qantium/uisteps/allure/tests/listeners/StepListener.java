package com.qantium.uisteps.allure.tests.listeners;

import com.qantium.uisteps.allure.tests.BaseTest;
import com.qantium.uisteps.allure.tests.listeners.handlers.*;
import ru.yandex.qatools.allure.Allure;
import ru.yandex.qatools.allure.events.*;
import ru.yandex.qatools.allure.experimental.LifecycleListener;
import ru.yandex.qatools.allure.experimental.ListenersNotifier;
import ru.yandex.qatools.allure.model.Step;
import ru.yandex.qatools.allure.model.TestCaseResult;
import ru.yandex.qatools.allure.model.TestSuiteResult;
import ru.yandex.qatools.allure.storages.StepStorage;
import ru.yandex.qatools.allure.storages.TestCaseStorage;
import ru.yandex.qatools.allure.storages.TestSuiteStorage;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static com.qantium.uisteps.allure.tests.listeners.Event.*;

/**
 * Created by Anton Solyankin
 */
public class StepListener extends LifecycleListener {

    private final Set<EventHandler> handlers = new LinkedHashSet();
    private Step lastStep;
    private Set<Step> steps = new HashSet();
    private TestSuiteResult testSuite;
    private TestCaseResult testCase;
    private BaseTest test;
    private boolean stepIsFailed;
    private Throwable error;

    public StepListener() {
        testCase = getTestStorage().get();
        init();
    }

    public StepListener set(BaseTest test) {
        this.test = test;
        return this;
    }

    public void init() {
        ListenersNotifier notifier = getNotifier();
        List<LifecycleListener> listeners = notifier.getListeners();
        if (listeners.size() != 0) {
            testSuite = ((StepListener) listeners.get(0)).getTestSuite();
        }
        listeners.clear();

        add(new CatchAssertions());
        add(new TakeScreenshot());
        add(new TakePageSource());
        add(new TakeHar());
        add(new CloseBrowsers());
        add(new CleanTitles());
        add(new LogTests());

        listeners.add(this);
    }

    public BaseTest getTest() {
        return test;
    }

    public Step getLastStep() {
        return lastStep;
    }

    public TestCaseResult getTestCase() {
        return testCase;
    }

    public TestSuiteResult getTestSuite() {
        return testSuite;
    }

    public Set<Step> getSteps() {
        return steps;
    }


    public StepListener add(EventHandler handler) {
        handler.setListener(this);
        handlers.add(handler);
        return this;
    }

    @Override
    public void fire(TestSuiteEvent event) {
        if (event instanceof TestSuiteStartedEvent) {
            TestSuiteStartedEvent suiteStartedEvent = (TestSuiteStartedEvent) event;
            String testSuiteUID = suiteStartedEvent.getUid();
            testSuite = getSuiteStorage().get(testSuiteUID);
            fire(SUITE_STARTED);
        }
    }

    @Override
    public void fire(TestSuiteFinishedEvent event) {
        fire(SUITE_FINISHED);
    }

    @Override
    public void fire(StepStartedEvent event) {
        lastStep = getStepStorage().getLast();
        steps.add(getStepStorage().getLast());
        fire(STEP_STARTED);
    }

    @Override
    public void fire(TestCaseStartedEvent event) {
        fire(Event.TEST_STARTED);
    }

    @Override
    public void fire(TestCaseFinishedEvent event) {
        fire(TEST_FINISHED);
    }

    @Override
    public void fire(StepFinishedEvent event) {

        if (stepIsFailed) {
            stepIsFailed = false;
        } else {
            error = null;
        }
        fire(STEP_FINISHED);
    }

    public Throwable getError() {
        return error;
    }

    @Override
    public void fire(StepEvent event) {
        if (event instanceof StepFailureEvent) {
            stepIsFailed = true;
            StepFailureEvent failure = (StepFailureEvent) event;
            error = failure.getThrowable();
            fire(STEP_FAILED);
        }
    }

    public void fire(Event event, Object... args) {
        Exception exception = null;
        EventHandler failedHandler = null;
        for (EventHandler handler : handlers) {
            if (handler.needsOn(event)) {
                try {
                    handler.handle(event, args);
                } catch (Exception ex) {
                    failedHandler = handler;
                    exception = ex;
                }
            }
        }
        if (exception != null) {
            throw new StepListenerException(failedHandler, exception);
        }
    }

    public StepStorage getStepStorage() {
        return get(StepStorage.class, "stepStorage");
    }

    public TestCaseStorage getTestStorage() {
        return get(TestCaseStorage.class, "testCaseStorage");
    }

    public TestSuiteStorage getSuiteStorage() {
        return get(TestSuiteStorage.class, "testSuiteStorage");
    }

    public Allure getAllure() {
        return get(Allure.class, "LIFECYCLE");
    }

    public ListenersNotifier getNotifier() {
        return get(ListenersNotifier.class, "notifier");
    }

    private <T> T get(Class<T> fieldType, String fieldName) {
        try {
            Field field = Allure.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return (T) field.get(Allure.LIFECYCLE);
        } catch (NoSuchFieldException | IllegalAccessException ex) {
            throw new RuntimeException("Cannot get " + fieldName + " from Allure.LIFECYCLE!", ex);
        }
    }
}
