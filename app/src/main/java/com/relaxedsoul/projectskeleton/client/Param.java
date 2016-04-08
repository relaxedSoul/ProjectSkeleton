package com.relaxedsoul.projectskeleton.client;

/**
 * Created by yurchenko on 28.03.2016.
 */
public enum Param {
    AUTH("auth");

    private String value;

    Param(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}