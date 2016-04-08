package com.relaxedsoul.projectskeleton.client;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public final class RequestParams {

    private final RequestType type;
    private final Path path;
    private final Map<String, String> params;
    private final MediaType mediaType;
    private final String body;

    public RequestParams(RequestType type, Path path, Map<String, String> params, MediaType mediaType, String data) {
        this.type = type;
        this.path = path;
        this.params = params;
        this.mediaType = mediaType;
        this.body = data;
    }

    public RequestType getType() {
        return type;
    }

    public Path getPath() {
        return path;
    }

    public String buildParams(boolean urlEncoded) {
        if (params.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            sb.append(first ? urlEncoded ? "" : "?" : "&").append(entry.getKey()).append("=");
            if (urlEncoded) {
                try {
                    sb.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    sb.append(entry.getValue());
                }
            } else sb.append(entry.getValue());
            first = false;
        }
        return sb.toString();
    }

    public RequestBody getBody() {
        return RequestBody.create(mediaType, body);
    }

    public String getBodyString() {
        return body;
    }

    public static class Builder {
        private Map<String, String> params;
        private MediaType mediaType;
        private String data;
        private Path path;
        private RequestType type;

        public Builder() {
            params = new HashMap<>();
            mediaType = MediaType.parse("application/json");
            type = RequestType.GET;
        }

        public Builder requestData(String data) {
            this.data = data;
            return this;
        }

        public Builder requestData(MediaType mediaType, String data) {
            this.mediaType = mediaType;
            this.data = data;
            return this;
        }

        public Builder path(String baseUrl) {
            path = new Path(baseUrl);
            return this;
        }

        public Builder path(String baseUrl, String... items) {
            path = new Path(baseUrl);
            for (String item : items) {
                path.add(item);
            }
            return this;
        }

        public Builder type(RequestType type) {
            this.type = type;
            return this;
        }

        public Builder addParam(String name, String value) {
            if (value != null) params.put(name, value);
            return this;
        }

        public RequestParams build() {
            return new RequestParams(type, path, params, mediaType, data);
        }
    }
}