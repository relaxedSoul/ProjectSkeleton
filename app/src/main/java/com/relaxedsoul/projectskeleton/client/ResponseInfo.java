package com.relaxedsoul.projectskeleton.client;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.relaxedsoul.projectskeleton.util.LogHelper;

import java.lang.reflect.Type;

public final class ResponseInfo<T extends BaseJson> {

    private static final String TAG = "ResponseInfo";
    private final Class<? extends T> contentClazz;
    private BaseResponseJson<T> responseJson;

    private ResponseInfo(Class<T> contentClazz, String body, Type type, boolean isMap) {
        this.contentClazz = contentClazz;
        if (type == null) parseObjectJson(body);
        else if (isMap) parseMapFromJson(body, type);
        else parseListFromJson(body, type);
    }

    public static <T extends BaseJson> BaseResponseJson<T> onSuccess(Class<T> containerClazz,
                                                                     String body, Type type,
                                                                     boolean isMap) {
        return new ResponseInfo<>(containerClazz, body, type, isMap).getResponseJson();
    }

    public static <T extends BaseJson> BaseResponseJson<T> onHttpFailure() {
        return new BaseResponseJson<>(BaseResponseJson.RESPONSE_HTTP_FAILURE, "Response failed");
    }

    public static <T extends BaseJson> BaseResponseJson<T> onNetworkFailure() {
        return new BaseResponseJson<>(BaseResponseJson.RESPONSE_HTTP_FAILURE, "Call failed");
    }

    private BaseResponseJson<T> onSuccess() {
        return new BaseResponseJson<>();
    }

    private BaseResponseJson<T> getResponseJson() {
        return responseJson;
    }

    private BaseResponseJson<T> onParsingResponseFailure() {
        return new BaseResponseJson<>(BaseResponseJson.RESPONSE_JSON_PARSE_FAILURE, "Invalid json");
    }

    protected void parseObjectJson(String jsonString) {
        try {
            responseJson = onSuccess().setResult(new Gson().fromJson(jsonString, contentClazz));
        } catch (IllegalStateException | JsonSyntaxException e) {
            responseJson = onParsingResponseFailure();
        }
    }

    protected void parseMapFromJson(String body, Type mapType) {
        try {
            responseJson = onSuccess().setMap(new Gson().fromJson(body, mapType));
        } catch (IllegalStateException | JsonSyntaxException e) {
            LogHelper.e(TAG, "Wrong json body:\n" + body);
            LogHelper.printStackTrace(e);
            responseJson = onParsingResponseFailure();
        }
    }

    protected void parseListFromJson(String body, Type listType) {
        try {
            responseJson = onSuccess().setList(new Gson().fromJson(body, listType));
        } catch (IllegalStateException | JsonSyntaxException e) {
            LogHelper.e(TAG, "Wrong json body:\n" + body);
            LogHelper.printStackTrace(e);
            responseJson = onParsingResponseFailure();
        }
    }
}
