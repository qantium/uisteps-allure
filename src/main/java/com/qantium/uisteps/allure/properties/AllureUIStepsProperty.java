package com.qantium.uisteps.allure.properties;

import com.qantium.uisteps.core.properties.IUIStepsProperty;

import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * Created by Anton Solyankin
 */
public enum AllureUIStepsProperty implements IUIStepsProperty {

    ALLURE_HOME_DIR("/target/site/allure-maven-plugin/data"),
    ALLURE_LOG_ATTACH("true");

    private final String defaultValue;

    AllureUIStepsProperty(String defaultValue) {
        String key = this.toString();

        if(isEmpty(System.getProperty(key)) && !isEmpty(defaultValue)) {
            System.setProperty(key, defaultValue);
        }

        this.defaultValue = defaultValue;
    }

    AllureUIStepsProperty() {
        this("");
    }

    @Override
    public String getDefaultValue() {
        return defaultValue;
    }

    @Override
    public String toString() {
        return name().toLowerCase().replace("_", ".");
    }
}
