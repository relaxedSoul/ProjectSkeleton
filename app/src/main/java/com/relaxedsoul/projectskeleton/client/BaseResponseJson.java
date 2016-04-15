package com.relaxedsoul.projectskeleton.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BaseResponseJson<T extends BaseJson> {

    // Отрицательные - локальные или серверные особенные.
    // Коды ответов Http не стоит переопределять под другие нужды
    public static final int RESPONSE_SUCCESS = 200;
    public static final int RESPONSE_HTTP_FAILURE = 401;
    public static final int RESPONSE_JSON_PARSE_FAILURE = -2;
    public static final int RESPONSE_NO_NETWORK = -3;

    public static final String DEFAULT_MESSAGE = "";
    private int status = RESPONSE_SUCCESS;
    private String message = DEFAULT_MESSAGE;
    private String error = DEFAULT_MESSAGE;
    private T result;
    private Map<String, T> map;
    private List<T> list;

    public BaseResponseJson() {
    }

    public BaseResponseJson(int status, String message) {
        this.status = status;
        this.message = message;
        this.error = message;
    }

    public final boolean isSuccessFull() {
        return status == RESPONSE_SUCCESS;
    }

    public final int getResponseCode() {
        return status;
    }

    public final void setResponseCode(int responseCode) {
        status = responseCode;
    }

    public final String getMessage() {
        return getClass().getSimpleName() + ": " + message;
    }

    public final String getError() {
        return error;
    }

    public final boolean isNoNetwork() {
        return status == BaseResponseJson.RESPONSE_NO_NETWORK;
    }

    public final void setResponseMessage(String message) {
        this.message = message;
        this.error = message;
    }

    public final Map<String, T> getMap() {
        return map;
    }

    public final BaseResponseJson<T> setMap(Map<String, T> map) {
        this.map = map == null ? new HashMap<>() : new HashMap<>(map);
        return this;
    }

    public final List<T> getList() {
        return list;
    }

    public final BaseResponseJson<T> setList(List<T> list) {
        this.list = list == null ? new ArrayList<>(0) : list;
        return this;
    }

    public final T getResult() {
        return result;
    }

    public final BaseResponseJson<T> setResult(T result) {
        this.result = result;
        return this;
    }
}
