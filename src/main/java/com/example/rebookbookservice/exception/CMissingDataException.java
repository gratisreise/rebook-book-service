package com.example.rebookbookservice.exception;


public class CMissingDataException extends RuntimeException {
    public CMissingDataException() {
        super();
    }
    public CMissingDataException(String message) {
        super(message);
    }
    public CMissingDataException(String message, Throwable cause) {
        super(message, cause);
    }
    public CMissingDataException(Throwable cause) {
        super(cause);
    }
}
