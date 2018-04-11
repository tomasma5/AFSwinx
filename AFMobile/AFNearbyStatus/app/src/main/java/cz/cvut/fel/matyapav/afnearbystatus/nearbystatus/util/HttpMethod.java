package cz.cvut.fel.matyapav.afnearbystatus.nearbystatus.util;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public enum HttpMethod {

    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE");

    private String method;

    private HttpMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    @Override
    public String toString() {
        return method;
    }
}
