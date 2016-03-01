package com.qantium.uisteps.allure.tests.listeners.functions;

import com.qantium.uisteps.allure.tests.listeners.Event;
import com.qantium.uisteps.allure.tests.listeners.Meta;
import com.qantium.uisteps.core.tests.MetaInfo;
import com.qantium.uisteps.core.utils.testrail.TestRailAdapter;
import com.qantium.uisteps.core.utils.testrail.TestRailEntity;
import com.qantium.uisteps.core.utils.testrail.TestRailType;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.yandex.qatools.allure.model.Label;
import ru.yandex.qatools.allure.model.Parameter;
import ru.yandex.qatools.allure.model.Step;
import ru.yandex.qatools.allure.model.TestCaseResult;

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
            if("testId".equals(label.getName())) {

                String caseId = label.getValue();
                if (caseId.startsWith(TestRailType.CASE.mark)) {

                    int status = getTestRailStatusCodeFor(testResult);
                    JSONArray testsJSONArray = TestRailAdapter.getInstance().getData().getJSONArray("test");

                    for(int i = 0; i < testsJSONArray.length(); i++) {

                        try {
                            JSONObject testJSON = testsJSONArray.getJSONObject(i);

                            if(new TestRailEntity(caseId).getId() == testJSON.getInt("case_id")) {
                                int testId = testJSON.getInt("id");
                                testRailAdapter.addTestResult(testId, status);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            }
        }

        System.out.println("##### " + testResult.getStart() + " " + testResult.getStop() + " " + testResult.getDescription() + " " + testResult.getFailure().getMessage() + testResult.getFailure().getMessage());

        for (Parameter parameter : testResult.getParameters()) {
            System.out.println("@@@@" + " " + parameter.getKind() + " " + parameter.getName() + " " + parameter.getValue());
        }

        for (Step step: getListener().getSteps()) {
            System.out.println("****" + " " + step.getTitle() + " " + step.getName());
        }
        return null;
    }

    private String buildTestResultComment(TestCaseResult testResult) {
        StringBuffer comment = new StringBuffer();
        comment
                .append("[INFO] ------------------------------------------------------------------------")
                .append(appendLabelsToTestResultComment(testResult))
                .append("-------------------------------------------------------------------------------");
        return comment.toString();
    }

    private StringBuffer appendLabelsToTestResultComment(TestCaseResult testResult) {
        StringBuffer comment = new StringBuffer();

        comment.append("Labels:\n");
        for (Label label : testResult.getLabels()) {
            comment
                    .append("\u0009")
                    .append(label.getName())
                    .append(": ")
                    .append(label.getValue());
        }
        return comment.append("\n");
    }

    private void appendTimingsToTestResultComment(StringBuffer comment, TestCaseResult testResult) {
        comment.append("Timings:\n");
        for (Label label : testResult.getLabels()) {
            comment.append(label.getName()).append(": ").append(label.getValue());
        }
    }

    protected int getTestRailStatusCodeFor(TestCaseResult testResult) {
        return testRailAdapter.getStatusCode(testResult.getStatus().name());cd
    }

    @Override
    public boolean needsOn(Event event) {
        return super.needsOn(event) && TestRailAdapter.actionIsDefined();
    }
}
