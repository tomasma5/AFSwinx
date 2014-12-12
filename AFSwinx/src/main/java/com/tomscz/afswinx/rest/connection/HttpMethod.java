package com.tomscz.afswinx.rest.connection;

public enum HttpMethod {
    GET("get"), PUT("put"), POST("post"), DELETE("delete");

    private final String name;

    private HttpMethod(String name) {
        this.name = name;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public String toString() {
        return name;
    }
}
