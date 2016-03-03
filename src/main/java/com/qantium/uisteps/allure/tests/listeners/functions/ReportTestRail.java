package com.qantium.uisteps.allure.tests.listeners.functions;

import com.qantium.uisteps.allure.tests.listeners.Event;
import com.qantium.uisteps.core.utils.testrail.TestRailAdapter;
import com.qantium.uisteps.core.utils.testrail.TestRailEntity;
import com.qantium.uisteps.core.utils.testrail.TestRailType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.yandex.qatools.allure.model.Label;
import ru.yandex.qatools.allure.model.Parameter;
import ru.yandex.qatools.allure.model.Step;
import ru.yandex.qatools.allure.model.TestCaseResult;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Anton Solyankin
 */
public class ReportTestRail extends ListenerFunction {

    private TestRailAdapter testRailAdapter = TestRailAdapter.getInstance();

    public ReportTestRail() {
        super(new Event[]{Event.TEST_FINISHED});
    }

    @Override
    public Object execute() {
        TestCaseResult testResult = getListener().getTest();

        for (Label label : testResult.getLabels()) {

            String labelName = label.getName();

            if("testId".equals(labelName)) {

                for(String caseId: label.getValue().split(",")) {

                    if (caseId.trim().startsWith(TestRailType.CASE.mark)) {
                        int status = getTestRailStatusCodeFor(testResult);
                        JSONArray testsJSONArray = TestRailAdapter.getInstance().getData().getJSONArray("test");

                        for (int i = 0; i < testsJSONArray.length(); i++) {

                            try {
                                JSONObject testJSON = testsJSONArray.getJSONObject(i);

                                if (new TestRailEntity(caseId).getId() == testJSON.getInt("case_id")) {
                                    int testId = testJSON.getInt("id");
                                    testRailAdapter.addTestResult(testId, status);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                break;
            }
        }

        System.out.println(buildTestResultComment(testResult));
        return null;
    }

    private String buildTestResultComment(TestCaseResult testResult) {
        StringBuffer comment = new StringBuffer();
        comment
                .append("[INFO] ------------------------------------------------------------------------")
                .append(getLabels(testResult))
                .append(getTimings(testResult))
                .append(getParameters(testResult))
                .append(getSteps(testResult))
                .append("\n-------------------------------------------------------------------------------");
        return comment.toString();
    }

    private StringBuffer getLabels(TestCaseResult testResult) {
        StringBuffer labels = new StringBuffer();

        labels.append("\nLabels:");
        for (Label label : testResult.getLabels()) {
            labels
                    .append("\n\t")
                    .append(label.getName())
                    .append(": ")
                    .append(label.getValue());
        }
        return labels;
    }

    private StringBuffer getTimings(TestCaseResult testResult) {

        StringBuffer timings = new StringBuffer();

        long start = testResult.getStart();
        long stop = testResult.getStop();
        long total = stop - start;

        timings
                .append("\nTimings:")
                .append("\n\tstart: ").append(new Date(start))
                .append("\n\tstop: ").append(new Date(stop))
                .append("\n\ttotal: ").append(new SimpleDateFormat("m'm 's's 'SSS'ms'").format(total));

        return timings;
    }

    private StringBuffer getParameters(TestCaseResult testResult) {
        StringBuffer parameters = new StringBuffer();

        parameters.append("Parameters:");

        for (Parameter parameter : testResult.getParameters()) {
            parameters
                    .append("\n\t")
                    .append(parameter.getName())
                    .append(": ")
                    .append(parameter.getValue());
        }
        return parameters;
    }

    private StringBuffer getSteps(TestCaseResult testResult) {
        StringBuffer parameters = new StringBuffer();

        parameters.append("Steps:");

        for (Step step : testResult.getSteps()) {
            parameters
                    .append("\n\t")
                    .append(step.getTitle());
        }
        return parameters;
    }

    protected int getTestRailStatusCodeFor(TestCaseResult testResult) {
        return testRailAdapter.getStatusCode(testResult.getStatus().name());
    }

    @Override
    public boolean needsOn(Event event) {
        return super.needsOn(event) && TestRailAdapter.actionIsDefined();
    }
}
