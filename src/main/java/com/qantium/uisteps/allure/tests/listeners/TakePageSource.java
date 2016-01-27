package com.qantium.uisteps.allure.tests.listeners;

import com.qantium.uisteps.allure.user.User;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;

/**
 * Created by SolAn on 20.01.2016.
 */
public class TakePageSource extends UserFunction {

    public TakePageSource(User user) {
        super(user, "source.take");
    }

    @Override
    public boolean needsOn(Event event) {
        return super.needsOn(event) && getUser().getBrowserManager().hasAny();
    }

    @Override
    public String execute() {
        return attachPageSource();
    }

    @Step("Attach page source META[listen=false]")
    protected String attachPageSource() {
        String pageSource = getUser().inOpenedBrowser().getDriver().getPageSource();
        attachPageSource(pageSource);
        return pageSource;
    }

    @Attachment(value = "page source", type = "text/plain")
    protected String attachPageSource(String pageSource) {
        return pageSource;
    }
}
