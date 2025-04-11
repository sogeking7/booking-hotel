package org.todo.utils;

public class BusinessException extends Exception {
    private int status;
    private String description;

    public BusinessException(int status, String message, String description) {
        super(message);
        this.status = status;
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
