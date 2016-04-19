package com.qantium.uisteps.allure.properties;

import com.qantium.uisteps.core.properties.IUIStepsProperty;

import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * Created by Anton Solyankin
 */
public enum AllureUIStepsProperty implements IUIStepsProperty {

    ALLURE_HOME_DIR("/target/site/allure-maven-plugin/data"),

    ALLURE_LOG_DIR(ALLURE_HOME_DIR.getDefaultValue()),
    ALLURE_LOG_ATTACH("true");

    private String defaultValue = "";

    AllureUIStepsProperty(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    AllureUIStepsProperty() {
    }

    @Override
    public String getDefaultValue() {
        if (isEmpty(defaultValue)) {
            return System.getProperty(this.toString());
        } else {
            return defaultValue;
        }
    }

    @Override
    public String toString() {
        return name().toLowerCase().replace("_", ".");
    }
}
