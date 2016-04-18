package com.relaxedsoul.projectskeleton.client.impl;

import com.relaxedsoul.projectskeleton.client.BaseJson;
import com.relaxedsoul.projectskeleton.client.ResponseInfo;

import java.lang.reflect.Type;

/**
 * Created by RelaxedSoul on 15.04.2016.
 */
public class ResponseInfoImpl<T extends BaseJson> extends ResponseInfo<T> {
    private ResponseInfoImpl(Class<T> contentClazz, String body, Type type, boolean isMap) {
        super(contentClazz, body, type, isMap);
    }
}
