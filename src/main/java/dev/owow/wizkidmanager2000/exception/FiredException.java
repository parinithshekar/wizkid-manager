package dev.owow.wizkidmanager2000.exception;

public class FiredException extends RuntimeException {
    public FiredException() {
    }

    public FiredException(String message) {
        super(message);
    }

    public FiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public FiredException(Throwable cause) {
        super(cause);
    }
}
