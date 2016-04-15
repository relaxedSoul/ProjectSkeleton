package com.relaxedsoul.projectskeleton.client.impl;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.relaxedsoul.projectskeleton.MyApp;
import com.relaxedsoul.projectskeleton.client.BaseResponseJson;
import com.relaxedsoul.projectskeleton.client.BaseServerApi;
import com.relaxedsoul.projectskeleton.client.Param;
import com.relaxedsoul.projectskeleton.client.RequestParams;
import com.relaxedsoul.projectskeleton.client.RequestType;
import com.relaxedsoul.projectskeleton.client.impl.model.RequestBodyJson;
import com.relaxedsoul.projectskeleton.client.impl.model.ResponseBodyJson;
import com.squareup.okhttp.OkHttpClient;

import java.net.URL;
import java.util.concurrent.TimeUnit;

public class ServerApi extends BaseServerApi<ResponseInfoImpl> {

    private static final long CONNECTION_TIMEOUT = 10;
    private static final long READ_TIMEOUT = 40;
    private static final long WRITE_TIMEOUT = 10;
    private static ServerApi sInstance;
    private Context mContext;
    private Gson mGson;
    private OkHttpClient mClient;

    private ServerApi() {
        mContext = MyApp.getInstance().getApplicationContext();
        mGson = new GsonBuilder().disableHtmlEscaping().create();
    }

    public static synchronized ServerApi getInstance() {
        if (sInstance == null) sInstance = new ServerApi();
        return sInstance;
    }

    public static ServerApi getInstance(URL url) {
        ServerApi api = getInstance();
        api.setHttpUrl(url);
        return api;
    }

    @NonNull
    @Override
    protected Context getContext() {
        return mContext;
    }

    @NonNull
    @Override
    protected Gson getGson() {
        return mGson;
    }

    @NonNull
    @Override
    protected synchronized OkHttpClient getClient() {
        if (mClient != null) return mClient;
        mClient = new OkHttpClient();
        mClient.setConnectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        mClient.setReadTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
        mClient.setWriteTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
        return mClient;
    }

    public BaseResponseJson<ResponseBodyJson> someBusinessAction() {
        return performRequest(new RequestParams.Builder()
                        .path("http://example.com/", "Users", ".json")
                        .addParam(Param.AUTH.value(), "token")
                        .requestData(getGson().toJson(new RequestBodyJson(0)))
                        .type(RequestType.PATCH)
                        .build(),
                ResponseBodyJson.class);
    }
}
