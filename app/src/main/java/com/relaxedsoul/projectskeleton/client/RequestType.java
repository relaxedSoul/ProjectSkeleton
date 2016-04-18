package com.relaxedsoul.projectskeleton.client;

/**
 * Created by RelaxedSoul on 03.04.2016.
 */
public enum RequestType {
    GET("GET"), POST("POST"), PATCH("PATCH"), DELETE("DELETE");

    private String value;

    RequestType(String value) {
        this.value = value;
    }

    public String val() {
        return value;
    }
}
