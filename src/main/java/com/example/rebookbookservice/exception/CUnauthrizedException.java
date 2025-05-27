package com.example.rebookbookservice.exception;

public class CUnauthrizedException extends RuntimeException {

    public CUnauthrizedException(String message) {
        super(message);
    }
    public CUnauthrizedException(String message, Throwable cause) {
      super(message, cause);
    }
    public CUnauthrizedException(Throwable cause) {
      super(cause);
    }
    public CUnauthrizedException() {
      super();
    }
}
