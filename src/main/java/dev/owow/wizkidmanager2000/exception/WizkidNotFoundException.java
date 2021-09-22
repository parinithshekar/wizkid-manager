package dev.owow.wizkidmanager2000.exception;

public class WizkidNotFoundException extends RuntimeException {

    public WizkidNotFoundException() {
    }

    public WizkidNotFoundException(String message) {
        super(message);
    }

    public WizkidNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public WizkidNotFoundException(Throwable cause) {
        super(cause);
    }
}
