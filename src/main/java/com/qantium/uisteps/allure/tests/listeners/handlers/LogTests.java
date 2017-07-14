package com.qantium.uisteps.allure.tests.listeners.handlers;

import com.google.common.io.Files;
import com.qantium.uisteps.allure.tests.listeners.Event;
import com.qantium.uisteps.core.lifecycle.MetaInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.qatools.allure.model.*;

import java.io.File;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.qantium.uisteps.allure.properties.AllureUIStepsProperty.ALLURE_HOME_DIR;
import static com.qantium.uisteps.allure.properties.AllureUIStepsProperty.ALLURE_LOG_ATTACH;
import static com.qantium.uisteps.allure.tests.listeners.Event.*;
import static com.qantium.uisteps.core.properties.UIStepsProperty.USER_DIR;
import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * Created by Anton Solyankin
 */
public class LogTests extends EventHandler {

    private List<String> log = new ArrayList();
    private Charset UTF_8 = Charset.forName("UTF-8");
    private String dir = USER_DIR.getValue() + ALLURE_HOME_DIR.getValue();

    public LogTests() {
        super(new Event[]{TEST_STARTED, TEST_FINISHED, STEP_STARTED, ASSERT, ASSERT_BROKEN, STEP_FAILED});
    }

    @Override
    public List<String> handle(Event event, Object... args) {

        switch (event) {
            case TEST_STARTED:
                logTest(TEST_STARTED);
                break;
            case TEST_FINISHED:
                logTest(TEST_FINISHED);
                attachLog();
                writeLog();
                log = new ArrayList();
                break;
            case STEP_STARTED:
                logStepStarted();
                break;
            case ASSERT:
            case ASSERT_BROKEN:
                String message = args[0].toString();
                logAssert(message);
                break;
            case STEP_FAILED:
                logStepFailed();
                break;
        }
        return log;
    }


    private void writeLog() {
        Logger logger = LoggerFactory.getLogger("Test log");
        try {
            for (String line : log) {
                logger.info(line);
            }
        } catch (Exception ex) {
            throw new RuntimeException("Cannot write log!", ex);
        }
    }

    private void attachLog() {
        if (ALLURE_LOG_ATTACH.isTrue()) {
            UUID uid = UUID.randomUUID();
            String fileName = "log-" + uid + ".log";
            File file = new File(dir, fileName);
            try {
                if (!file.exists()) {
                    Files.createParentDirs(file);
                }
                for (String line : log) {
                    Files.append(line + "\n", file, UTF_8);
                }
            } catch (Exception ex) {
                throw new RuntimeException("Cannot attach log!", ex);
            }

            long size = file.length();
            Attachment attachment = new Attachment();
            attachment.withSource(fileName)
                    .withType("text/plain")
                    .withTitle("log")
                    .withSize((int) size);

            getListener().getTestCase().getAttachments().add(attachment);
        }
    }

    private void logTest(Event event) {
        TestSuiteResult suite = getListener().getTestSuite();
        String suiteTitle = suite.getTitle();
        if (isEmpty(suiteTitle)) {
            suiteTitle = suite.getName();
        }

        TestCaseResult testCase = getListener().getTestCase();
        String testTitle = testCase.getTitle();
        if (isEmpty(testTitle)) {
            testTitle = testCase.getName();
        }
        log.add("------------------------------------------------------------------------------------------------------");

        long time;

        if (event == TEST_STARTED) {
            time = testCase.getStart();
        } else {
            time = testCase.getStop();
        }

        log.add(event + ": " + new SimpleDateFormat("hh:mm:ss").format(time));

        if (event == TEST_FINISHED) {

            ArrayList<String> startedLog = new ArrayList<>();

            startedLog.add("TITLE: " + new MetaInfo(testTitle).getTitleWithoutMeta());
            startedLog.add("SUITE: " + suiteTitle);
            startedLog.add("LABELS: ");
            for (Label label : testCase.getLabels()) {
                if (!label.getName().equals("host") && !label.getName().equals("thread")) {
                    startedLog.add("> " + label.getName() + ": " + label.getValue());
                }
            }
            log.addAll(2, startedLog);

            log.add("STATUS: " + testCase.getStatus());
            long total = testCase.getStop() - testCase.getStart();
            log.add("TOTAL: " + new SimpleDateFormat("mm'm' ss's' SSS'ms'").format(total));
        }

        log.add("------------------------------------------------------------------------------------------------------");
    }


    private void logStepStarted() {
        Step step = getListener().getLastStep();
        String title = step.getTitle();
        if (isEmpty(title)) {
            title = step.getName();
        }

        title = new MetaInfo(title).getTitleWithoutMeta();
        String time = new SimpleDateFormat("hh:mm:ss:SSS").format(step.getStart());

        title = "[" + time + "] " + title;
        log.add(title);
    }

    private void logAssert(String message) {
        String title = message;
        String time =  new SimpleDateFormat("hh:mm:ss:SSS").format(System.currentTimeMillis());

        title = "[" + time + "] " + title;
        log.add(title);
    }

    private void logStepFailed() {
        Throwable error = getListener().getError();
        if (error != null) {
            log.add("ERROR: " + error.getMessage());
            log.add("CAUSE: " + error.getCause());
            StackTraceElement[] stackTrace = error.getStackTrace();
            log.add("STACKTRACE: ");

            for (StackTraceElement stackTraceElement : stackTrace) {
                log.add("    > " + stackTraceElement);
            }
        }
    }
}
