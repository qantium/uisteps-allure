package com.qantium.uisteps.allure.tests.listeners.functions;

import com.qantium.uisteps.allure.browser.BrowserManager;
import com.qantium.uisteps.allure.tests.listeners.Event;
import com.qantium.uisteps.allure.tests.listeners.Meta;
import com.qantium.uisteps.core.properties.UIStepsProperty;
import com.qantium.uisteps.core.tests.MetaInfo;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;


/**
 * Created by Anton Solyankin
 */
public class TakePageSource extends ListenerFunction {

    public TakePageSource() {
        super(UIStepsProperty.SOURCE_TAKE.toString());
    }

    @Override
    public boolean needsOn(Event event) {
        return super.needsOn(event) && getBrowserManager().hasAny();
    }

    @Override
    public String execute() {
        return attachPageSource();
    }

    @Step("Attach page source META[listen=false]")
    protected String attachPageSource() {
        String pageSource = getBrowserManager().getCurrentBrowser().getDriver().getPageSource();
        attachPageSource(pageSource);
        return pageSource;
    }

    @Attachment(value = "page source", type = "text/plain")
    protected String attachPageSource(String pageSource) {
        return pageSource;
    }

    private BrowserManager getBrowserManager() {
        return new BrowserManager();
    }
}
