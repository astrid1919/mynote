package com.example.mynote.model;

public class Response {
    private String message;
    private Object data;
    private int statusCode;

    public Response(String message, Object data, int statusCode) {
        this.message = message;
        this.data = data;
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public int getStatusCode() {
        return statusCode;
    }
}