package com.qantium.uisteps.allure.verify;

import com.qantium.uisteps.core.verify.results.Result;
import org.junit.Assert;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;

/**
 * Created by Anton Solyankin
 */
public class Verify extends com.qantium.uisteps.core.verify.Verify {

    @Step("Verify result")
    @Override
    public void check(Result result) {
        seeResult("see result", result.toString());
        Assert.assertTrue("Verification is failed", result.isSuccessful());
    }

    @Attachment(value = "{0}", type = "text/html")
    protected String seeResult(String resultName, String result) {
        return result;
    }
}
