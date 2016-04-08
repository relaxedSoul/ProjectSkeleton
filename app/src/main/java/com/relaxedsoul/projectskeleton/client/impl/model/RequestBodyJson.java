package com.relaxedsoul.projectskeleton.client.impl.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yurchenko on 08.04.2016.
 */
public class RequestBodyJson {

    @SerializedName("request_body_field")
    public int field;

    public RequestBodyJson(int field) {
        this.field = field;
    }
}
