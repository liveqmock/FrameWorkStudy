package com.ps.utils;

import com.ps.custom.entity.main.User;
import com.ps.custom.shiro.ShiroUser;
import org.apache.shiro.subject.Subject;

/**
 * @Package com.ps.utils
 * @Description
 * @Date 14-3-3
 * @USER saxisuer
 */
public abstract class SecurityUtils {

    public static User getLoginUser() {
        return getShiroUser().getUser();
    }

    public static ShiroUser getShiroUser() {
        Subject subject = getSubject();
        ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();
        return shiroUser;
    }

    public static Subject getSubject() {
        return org.apache.shiro.SecurityUtils.getSubject();
    }
}
