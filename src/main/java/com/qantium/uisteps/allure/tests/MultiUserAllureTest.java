package com.qantium.uisteps.allure.tests;

import com.qantium.uisteps.allure.user.UserFactory;
import com.qantium.uisteps.core.tests.MultiUserTest;
import com.qantium.uisteps.core.user.User;
import org.openqa.selenium.WebDriver;

/**
 * Created by Anton Solyankin
 */
public class MultiUserAllureTest extends MultiUserTest {


    public MultiUserAllureTest(UserFactory users) {
        super(users);
    }

    public MultiUserAllureTest() {
        this(new UserFactory());
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

}
