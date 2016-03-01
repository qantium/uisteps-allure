package com.qantium.uisteps.allure.tests;

import com.qantium.uisteps.allure.storage.Storage;
import com.qantium.uisteps.allure.tests.listeners.StepListener;
import com.qantium.uisteps.allure.tests.listeners.functions.ClearTitles;
import com.qantium.uisteps.allure.tests.listeners.functions.ReportTestRail;
import com.qantium.uisteps.allure.tests.listeners.functions.TakePageSource;
import com.qantium.uisteps.allure.tests.listeners.functions.TakeScreenshot;
import com.qantium.uisteps.allure.user.UserFactory;
import com.qantium.uisteps.allure.verify.Assume;
import com.qantium.uisteps.allure.verify.Verify;
import com.qantium.uisteps.core.tests.MultiUserTest;
import com.qantium.uisteps.core.user.User;
import ru.yandex.qatools.allure.Allure;

/**
 * Created by Anton Solyankin
 */
public class MultiUserAllureTest extends MultiUserTest {


    public MultiUserAllureTest(UserFactory users) {
        super(users);
        initListeners();
    }

    public MultiUserAllureTest() {
        this(new UserFactory());



    }

    protected void initListeners() {
        StepListener listener = new StepListener();

        listener
                .add(new TakeScreenshot())
                .add(new TakePageSource())
                .add(new ReportTestRail())
                .add(new ClearTitles());

        Allure.LIFECYCLE.addListener(listener);
    }


    @Override
    public User by(String user) {
        return users.by(user);
    }

    public void add(String user) {
        users.add(user);
    }

    @Override
    public void add(String name, Class<? extends User> user) {
        users.add(name, user);
    }

    @Override
    public <T extends User> T by(Class<T> user) {
        return users.by(user);
    }

    @Override
    public <T extends User> T by(String name, Class<T> user) {
        return users.by(name, user);
    }

    @Override
    protected Verify getAssertions() {
        return new Verify();
    }

    @Override
    protected Assume getAssumtions() {
        return new Assume();
    }

    @Override
    protected Storage getStorage() {
        return new Storage();
    }
}
