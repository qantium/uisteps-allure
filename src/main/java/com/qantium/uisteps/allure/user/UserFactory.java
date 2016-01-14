package com.qantium.uisteps.allure.user;


import ru.yandex.qatools.allure.annotations.Step;

/**
 * Created by Anton Solyankin
 */
public class UserFactory extends com.qantium.uisteps.core.user.UserFactory {

    public UserFactory() {
        super(com.qantium.uisteps.core.user.User.class);
    }

    public UserFactory(Class<? extends com.qantium.uisteps.core.user.User> user) {
        super(user);
    }

    @Step
    @Override
    public com.qantium.uisteps.core.user.User by(String user) {
        return super.by(user);
    }
}
