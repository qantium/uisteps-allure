package com.qantium.uisteps.allure.tests.listeners.handlers;

import com.google.common.io.Files;
import com.qantium.uisteps.allure.tests.listeners.Event;
import com.qantium.uisteps.core.lifecycle.MetaInfo;
import org.apache.commons.lang3.StringUtils;
import ru.yandex.qatools.allure.model.*;

import java.io.File;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import static com.qantium.uisteps.allure.properties.AllureUIStepsProperty.ALLURE_HOME_DIR;
import static com.qantium.uisteps.allure.properties.AllureUIStepsProperty.ALLURE_LOG_ATTACH;
import static com.qantium.uisteps.allure.tests.listeners.Event.*;
import static com.qantium.uisteps.core.properties.UIStepsProperty.USER_DIR;
import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * Created by Anton Solyankin
 */
public class LogTests extends EventHandler {

    private List<String> log;
    private Charset UTF_8 = Charset.forName("UTF-8");
    private String dir = USER_DIR.getValue() + ALLURE_HOME_DIR.getValue();

    public LogTests() {
        super(new Event[]{TEST_STARTED, TEST_FINISHED, STEP_STARTED, STEP_FAILED});
    }

    @Override
    public List<String> handle(Event event, Object... args) {

        switch (event) {
            case TEST_STARTED:
                log = new ArrayList();
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
            case STEP_FAILED:
                logStepFailed();
                break;
        }
        return log;
    }

    private void writeLog() {
        Logger logger = Logger.getLogger(LogTests.class.getName());
        logger.setUseParentHandlers(false);
        ConsoleHandler ch = new ConsoleHandler();
        ch.setFormatter(new LogFormatter());
        logger.addHandler(ch);

        for (String line : log) {
            logger.info(line);
        }
    }

    class LogFormatter extends Formatter {

        @Override
        public String format(LogRecord record) {
            StringBuilder sb = new StringBuilder();
            sb.append("[").append(record.getLevel()).append("]").append(' ');
            sb.append(record.getMessage()).append('\n');
            return sb.toString();
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

        title = "[" + time + "] " + getLevelSpace() + title;
        log.add(title);
    }

    private String getLevelSpace() {
        int level = getListener().getStepStorage().get().size() - 2;
        return StringUtils.repeat("    ", level);
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
