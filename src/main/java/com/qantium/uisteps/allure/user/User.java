package com.qantium.uisteps.allure.user;

import com.qantium.uisteps.allure.browser.BrowserManager;

/**
 * Created by Anton Solyankin
 */
public class User extends com.qantium.uisteps.core.user.User {

    public User() {
        setBrowserManager(new BrowserManager());
    }

}
