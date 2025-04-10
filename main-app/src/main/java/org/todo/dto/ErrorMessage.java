package org.todo.dto;

public class ErrorMessage {
    private String message;
    private int status;

    public ErrorMessage() {

    }

    public ErrorMessage(int status, String message) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return status;
    }

    public void setStatusCode(int status) {
        this.status = status;
    }
}

