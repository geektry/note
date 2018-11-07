package com.geektry.note.framework;

/**
 * @author Chaohang Fu
 */
public enum RuntimeExceptionMessage {

    /**
     * RuntimeExceptionMessage
     */
    AUTH_INVALID("权限不足~"),
    PARAM_NULL("参数不能有为空的~");

    private String message;

    RuntimeExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
