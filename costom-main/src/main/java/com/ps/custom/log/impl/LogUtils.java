package com.ps.custom.log.impl;

import com.ps.custom.SecurityConstants;
import com.ps.custom.log.LogMessageObject;

import javax.servlet.http.HttpServletRequest;

/**
 * @Package com.ps.custom.log.impl
 * @Description
 * @Date 14-3-3
 * @USER saxisuer
 */
public abstract class LogUtils {
    // 用于存储每个线程的request请求
    private static final ThreadLocal<HttpServletRequest> LOCAL_REQUEST = new ThreadLocal<HttpServletRequest>();

    public static void putRequest(HttpServletRequest request) {
        LOCAL_REQUEST.set(request);
    }

    public static HttpServletRequest getRequest() {
        return LOCAL_REQUEST.get();
    }

    public static void removeRequest() {
        LOCAL_REQUEST.remove();
    }

    /**
     * 将LogMessageObject放入LOG_ARGUMENTS。
     *
     * @param logMessageObject
     */
    public static void putArgs(LogMessageObject logMessageObject) {
        HttpServletRequest request = getRequest();
        request.setAttribute(SecurityConstants.LOG_ARGUMENTS, logMessageObject);
    }

    public static LogMessageObject getArgs() {
        HttpServletRequest request = getRequest();
        return (LogMessageObject) request.getAttribute(SecurityConstants.LOG_ARGUMENTS);
    }
}
