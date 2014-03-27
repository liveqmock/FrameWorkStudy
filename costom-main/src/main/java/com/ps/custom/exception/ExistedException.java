package com.ps.custom.exception;

/**
 * @Package com.ps.custom.exception
 * @Description
 * @Date 14-3-2
 * @USER saxisuer
 */
public class ExistedException extends ServiceException {
    /**
     * 描述
     */
    private static final long serialVersionUID = -598071452360556064L;

    public ExistedException() {
        super();
    }

    public ExistedException(String message) {
        super(message);
    }

    public ExistedException(Throwable cause) {
        super(cause);
    }

    public ExistedException(String message, Throwable cause) {
        super(message, cause);
    }
}
