package cz.cvut.fel.matyapav.afandroid.rest;

/**
 * This enum hold all method which can be used in connection XML specify file. Based on method will
 * be created concrete connection.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
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
