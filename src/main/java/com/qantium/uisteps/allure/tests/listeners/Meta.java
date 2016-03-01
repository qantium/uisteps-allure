package com.qantium.uisteps.allure.tests.listeners;

/**
 * Created by SolAN on 24.02.2016.
 */
public enum Meta {

    LISTEN, GROUPS, ATTACH_SCREENSHOT, ATTACH_SOURCE;

    @Override
    public String toString() {
        return name().replace("_", ".").toLowerCase();
    }
}
