package com.qantium.uisteps.allure.properties;

import com.qantium.uisteps.core.properties.IUIStepsProperty;

/**
 * Created by Anton Solyankin
 */
public enum AllureUIStepsProperty implements IUIStepsProperty {

    ALLURE_HOME_DIR("/target/site/allure-maven-plugin/data"),
    ALLURE_LOG_ATTACH("true");

    private final String defaultValue;

    AllureUIStepsProperty() {
        this("");
    }

    AllureUIStepsProperty(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public String getDefaultValue() {
        return defaultValue;
    }

}
