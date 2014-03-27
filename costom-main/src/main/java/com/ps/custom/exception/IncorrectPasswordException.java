package com.ps.custom.exception;

/**
 * @Package com.ps.custom.exception
 * @Description
 * @Date 14-3-2
 * @USER saxisuer
 */
public class IncorrectPasswordException extends ServiceException {

    public IncorrectPasswordException() {
        super();
    }

    public IncorrectPasswordException(String message) {
        super(message);
    }

    public IncorrectPasswordException(Throwable cause) {
        super(cause);
    }

    public IncorrectPasswordException(String message, Throwable cause) {
        super(message, cause);
    }
}
