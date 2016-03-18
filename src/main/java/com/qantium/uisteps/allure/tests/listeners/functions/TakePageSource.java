package com.qantium.uisteps.allure.tests.listeners.functions;

import com.qantium.uisteps.allure.tests.BaseTest;
import com.qantium.uisteps.allure.tests.listeners.Event;
import com.qantium.uisteps.allure.tests.listeners.Meta;
import com.qantium.uisteps.core.browser.AlertException;
import com.qantium.uisteps.core.properties.UIStepsProperty;
import com.qantium.uisteps.core.lifecycle.MetaInfo;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;


/**
 * Created by Anton Solyankin
 */
public class TakePageSource extends ListenerFunction {

    public TakePageSource() {
        super(UIStepsProperty.SOURCE_TAKE.toString());
    }

    public boolean needsOn(Event event) {
        String listenMeta = "";
        String attachSource = "";
        ru.yandex.qatools.allure.model.Step lastStep = getListener().getLastStep();
        if(lastStep != null && lastStep.getTitle() != null) {
            MetaInfo meta = new MetaInfo(lastStep.getTitle());
            listenMeta = meta.get(Meta.LISTEN.toString());
            attachSource = meta.get(Meta.ATTACH_SOURCE.toString());
        }

        return super.needsOn(event)
                && !"false".equals(listenMeta)
                && !"false".equals(attachSource)
                && getListener().getTest().getBrowserManager().hasAny()
                && getListener().getTest().inOpenedBrowser().isAlive();
    }

    @Override
    public String execute() {
        return attachPageSource();
    }

    @Step("Attach page source META[listen=false]")
    protected String attachPageSource() {
        BaseTest test = getListener().getTest();
        return attachPageSource(test.getPageSource());
    }

    @Attachment(value = "page source", type = "text/plain")
    protected String attachPageSource(String pageSource) {
        return pageSource;
    }
}
