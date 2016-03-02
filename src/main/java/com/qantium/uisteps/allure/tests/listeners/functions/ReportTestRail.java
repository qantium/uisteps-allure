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
//
//        for (Label label : testResult.getLabels()) {
//            if("testId".equals(label.getName())) {
//
//                String caseId = label.getValue();
//                if (caseId.startsWith(TestRailType.CASE.mark)) {
//
//                    int status = getTestRailStatusCodeFor(testResult);
//                    JSONArray testsJSONArray = TestRailAdapter.getInstance().getData().getJSONArray("test");
//
//                    for(int i = 0; i < testsJSONArray.length(); i++) {
//
//                        try {
//                            JSONObject testJSON = testsJSONArray.getJSONObject(i);
//
//                            if(new TestRailEntity(caseId).getId() == testJSON.getInt("case_id")) {
//                                int testId = testJSON.getInt("id");
//                                testRailAdapter.addTestResult(testId, status);
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//                break;
//            }
//        }

        System.out.println("##### " + testResult.getStart() + " " + testResult.getStop() + " " + testResult.getDescription() + " " + testResult.getFailure().getMessage() + testResult.getFailure().getMessage());

        for (Parameter parameter : testResult.getParameters()) {
            System.out.println("@@@@" + " " + parameter.getKind() + " " + parameter.getName() + " " + parameter.getValue());
        }

        for (Step step: getListener().getSteps()) {
            System.out.println("****" + " " + step.getTitle() + " " + step.getName());
        }

        System.out.println(buildTestResultComment(testResult));
        return null;
    }

    private String buildTestResultComment(TestCaseResult testResult) {
        StringBuffer comment = new StringBuffer();
        comment
                .append("[INFO] ------------------------------------------------------------------------")
                .append("\n").append(getLabels(testResult))
                .append("\n").append(getTimings(testResult))
                .append("\n").append("-------------------------------------------------------------------------------");
        return comment.toString();
    }

    private StringBuffer getLabels(TestCaseResult testResult) {
        StringBuffer labels = new StringBuffer();

        labels.append("Labels:");
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
                .append("Timings:")
                .append("\n\tstart: ").append(new Date(start))
                .append("\n\tstop: ").append(new Date(stop))
                .append("\n\ttotal: ").append(new SimpleDateFormat("m'm 's's 'SSS'ms'").format(total));

        return timings;
    }

    protected int getTestRailStatusCodeFor(TestCaseResult testResult) {
        return testRailAdapter.getStatusCode(testResult.getStatus().name());
    }

    @Override
    public boolean needsOn(Event event) {
        return super.needsOn(event) && TestRailAdapter.actionIsDefined();
    }
}
