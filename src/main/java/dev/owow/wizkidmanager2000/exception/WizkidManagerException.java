package dev.owow.wizkidmanager2000.exception;

public class WizkidManagerException extends RuntimeException {
    public WizkidManagerException() {
    }

    public WizkidManagerException(String message) {
        super(message);
    }

    public WizkidManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public WizkidManagerException(Throwable cause) {
        super(cause);
    }
}
