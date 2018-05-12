package com.tomscz.afswinx.common;

public class ParameterMissingException extends Exception{

    public ParameterMissingException() {
    }

    public ParameterMissingException(String message) {
        super(message);
    }

    public ParameterMissingException(String message, Throwable cause) {
        super(message, cause);
    }
}
