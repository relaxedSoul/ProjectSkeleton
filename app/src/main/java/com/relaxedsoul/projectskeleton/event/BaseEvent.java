package com.relaxedsoul.projectskeleton.event;

public class BaseEvent {
    private final Type type;
    private String detailsMessage;
    private String localMessage;
    public BaseEvent(Type type) {
        this.type = type;
    }

    public boolean isSuccess() {
        return Type.SUCCESS.equals(type);
    }

    public Type getType() {
        return type;
    }

    public String getDetailsMessage() {
        return detailsMessage;
    }

    public BaseEvent setDetailsMessage(String detailsMessage) {
        this.detailsMessage = detailsMessage;
        return this;
    }

    public String getLocalMessage() {
        return localMessage;
    }

    public BaseEvent setLocalMessage(String localMessage) {
        this.localMessage = localMessage;
        return this;
    }

    public enum Type {
        SUCCESS, FAILURE, NETWORK, INVALID_INPUT
    }
}
