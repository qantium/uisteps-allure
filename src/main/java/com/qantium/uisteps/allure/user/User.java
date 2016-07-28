package com.qantium.uisteps.allure.user;

import com.qantium.uisteps.allure.browser.BrowserManager;
import com.qantium.uisteps.allure.storage.Storage;
import com.qantium.uisteps.core.browser.pages.UIElement;
import com.qantium.uisteps.core.screenshots.Ignored;
import com.qantium.uisteps.core.screenshots.Screenshot;

/**
 * Created by Anton Solyankin
 */
public class User extends com.qantium.uisteps.core.user.User {

    public User() {
        super(new BrowserManager(), new Storage());
    }

    @Override
    public Storage getStorage() {
        return (Storage) super.getStorage();
    }
}
