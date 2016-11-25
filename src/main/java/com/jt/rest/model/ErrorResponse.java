package com.jt.rest.model;

public class ErrorResponse {
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }
    public ErrorResponse(Exception e) {
        this.message = e.getMessage();
    }
    public String getMessage() {
        return this.message;
    }
}
