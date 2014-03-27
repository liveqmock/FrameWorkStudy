package com.ps.custom.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @Package com.ps.custom.shiro
 * @Description
 * @Date 14-3-1
 * @USER saxisuer
 */
public class CaptchaUsernamePasswordToken extends UsernamePasswordToken {
    /**
     * 描述
     */
    private static final long serialVersionUID = -3178260335127476542L;

    private String captcha;

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public CaptchaUsernamePasswordToken() {
        super();
    }

    public CaptchaUsernamePasswordToken(String userName,String password, boolean rememberMe, String host,String captcha) {
        super(userName, password, rememberMe, host);
        this.captcha = captcha;
    }
}
