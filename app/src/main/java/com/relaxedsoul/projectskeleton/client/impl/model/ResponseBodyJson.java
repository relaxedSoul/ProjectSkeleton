package com.relaxedsoul.projectskeleton.client.impl.model;

import com.google.gson.annotations.SerializedName;
import com.relaxedsoul.projectskeleton.client.BaseJson;

/**
 * Created by yurchenko on 08.04.2016.
 */
public class ResponseBodyJson extends BaseJson {

    @SerializedName("field_name")
    public String field;
}
