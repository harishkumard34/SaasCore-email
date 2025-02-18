package com.techpuram.saascore.exception;

public class CustomException extends RuntimeException {
    private final int code;
    private final Object details;

    public CustomException(int code, String message, Object details) {
        super(message);
        this.code = code;
        this.details = details;
    }

    public int getCode() {
        return code;
    }

    public Object getDetails() {
        return details;
    }
}
