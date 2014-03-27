package com.ps.custom.exception;


/**
 * @Package com.ps.custom.exception
 * @Description
 * @Date 14-3-2
 * @USER saxisuer
 */
public class NotExistedException extends ServiceException {

    /** 描述  */
    private static final long serialVersionUID = -598071452360556064L;

    public NotExistedException() {
        super();
    }

    public NotExistedException(String message) {
        super(message);
    }

    public NotExistedException(Throwable cause) {
        super(cause);
    }

    public NotExistedException(String message, Throwable cause) {
        super(message, cause);
    }
}
