package com.ps.custom.shiro;

import org.apache.shiro.authc.AuthenticationException;

/**
 * @Package com.ps.custom.shiro
 * @Description
 * @Date 14-3-1
 * @USER saxisuer
 */
public class IncorrectCaptchaException extends AuthenticationException {

    /**
     * 描述
     */
    private static final long serialVersionUID = 6146451562810994591L;

    public IncorrectCaptchaException() {
        super();
    }

    public IncorrectCaptchaException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectCaptchaException(String message) {
        super(message);
    }

    public IncorrectCaptchaException(Throwable cause) {
        super(cause);
    }
}
