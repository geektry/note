package com.geektry.note.framework;

/**
 * @author Chaohang Fu
 */
public class ServiceRuntimeException extends RuntimeException {

    public ServiceRuntimeException(RuntimeExceptionMessage exceptionMessage) {
        super(exceptionMessage.getMessage());
    }
}
