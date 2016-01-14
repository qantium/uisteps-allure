package com.qantium.uisteps.allure.verify;

import com.qantium.uisteps.core.verify.results.Result;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;

/**
 * Created by Anton Solyankin
 */
public class Assume extends com.qantium.uisteps.core.verify.Assume {

    @Step("Assume result")
    @Override
    public void result(Result result) {
        seeResult("see result", result);

        try {
            super.result(result);
        } catch (Exception ex) {
            throw new AssertionError();
        }
    }

    @Attachment(value = "{0}", type = "text/html")
    protected String seeResult(String resultName, Result result) {
        return result.toString();
    }
}
