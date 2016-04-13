package com.relaxedsoul.projectskeleton.client;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.relaxedsoul.projectskeleton.util.LogHelper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class BaseServerApi {

    protected static final String TAG = "BaseServerApi";
    private URL mStaticUrl;

    @SuppressWarnings("unused")
    public boolean hasActiveInternetConnection() {
        if (isNetworkAvailable()) {
            try {
                HttpURLConnection urlc = (HttpURLConnection) (new URL("http://clients3.google.com/generate_204")
                        .openConnection());
                urlc.setRequestProperty("User-Agent", "Android");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1500);
                urlc.setReadTimeout(1000);
                urlc.connect();
                return (urlc.getResponseCode() == 204 &&
                        urlc.getContentLength() == 0);
            } catch (IOException e) {
                LogHelper.e(TAG, "Error checking internet connection");
                LogHelper.printStackTrace(e);
            }
        } else {
            LogHelper.d(TAG, "No network available!");
        }
        return false;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    public void setHttpUrl(URL url) {
        mStaticUrl = url;
    }

    /**
     * Main method for calling API.
     *
     * @param params         - request parameters. Url, type of Request, url Parameters, request body if needed
     * @param containerClazz - extended from BaseResponseJson type, containing response result and data if it was returned
     * @param type           - type of data, returned in response. Have to set if it is complex structure
     * @param <T>            - type of data, returned in complex structure, item of Array or Value entry set of the map
     * @return Base response json, containing response code, response message and data, if any was returned
     */
    protected <T extends BaseJson> BaseResponseJson<T> performRequest(RequestParams params,
                                                                      Class<T> containerClazz, Type type, boolean isMap) {
        try {
            Response response = call(params);
            if (response == null) {
                LogHelper.e(TAG, "response = null");
                return ResponseInfo.onNetworkFailure();
            }
            LogHelper.i(TAG, "response code: " + response.code() + ", msg: " + response.message());
            if (!response.isSuccessful()) return ResponseInfo.onHttpFailure();
            String body = response.body().string();
            LogHelper.i(TAG, "response body: " + body);
            return ResponseInfo.onSuccess(containerClazz, body, type, isMap);
        } catch (IOException e) {
            LogHelper.e(TAG, "Error on network. IO.");
            LogHelper.printStackTrace(e);
            return ResponseInfo.onNetworkFailure();
        }
    }

    protected <T extends BaseJson> BaseResponseJson<T> performRequest(RequestParams params,
                                                                      Class<T> containerClazz) {
        return performRequest(params, containerClazz, null, false);
    }

    private Response call(RequestParams params) {
        LogHelper.i(TAG, params.getType().val()
                + "\n" + params.getPath().build() + params.buildParams(false)
                + "\n" + params.getBodyString());
        Request.Builder builder = new Request.Builder()
                .url((mStaticUrl == null ? params.getPath().build()
                        : mStaticUrl) + params.buildParams(false));
        switch (params.getType()) {
            case POST:
                builder.post(params.getBody());
                break;
            case PATCH:
                builder.patch(params.getBody());
                break;
            case DELETE:
                builder.delete();
                break;
            case GET:
            default:
                break;
        }
        return callRequest(builder.build());
    }

    public Response callRequest(Request request) {
        try {
            return getClient().newCall(request).execute();
        } catch (IOException e) {
            LogHelper.e(TAG, "Error on network. IO.");
            LogHelper.printStackTrace(e);
            return null;
        }
    }

    @NonNull
    protected abstract Context getContext();

    @NonNull
    protected abstract Gson getGson();

    @NonNull
    protected abstract OkHttpClient getClient();
}
