package com.relaxedsoul.projectskeleton.client;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yurchenko on 28.03.2016.
 */
public final class Path {

    private static final String SEPARATOR = "/";
    private final List<String> childList;
    private final String baseUrl;

    public Path(String baseUrl, String... childList) {
        this.baseUrl = baseUrl;
        this.childList = new ArrayList<>();
        if (childList.length == 0) return;
        for (String i : childList) add(i);
    }

    public Path add(String child) {
        childList.add(child);
        return this;
    }

    public String build() {
        if (baseUrl == null || baseUrl.length() == 0) return "";
        StringBuilder urlBuilder = new StringBuilder(baseUrl);
        if (!baseUrl.endsWith(SEPARATOR)) urlBuilder.append(SEPARATOR);
        if (!childList.isEmpty()) {
            for (int i = 0; i < childList.size(); i++) {
                urlBuilder.append(childList.get(i));
                if (i < childList.size() - 1) urlBuilder.append(SEPARATOR);
            }
        }
        return urlBuilder.toString();
    }
}