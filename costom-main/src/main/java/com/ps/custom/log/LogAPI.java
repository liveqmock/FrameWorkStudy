package com.ps.custom.log;

import java.util.Map;

/**
 * @Package com.ps.custom.log
 * @Description
 * @Date 14-3-3
 * @USER saxisuer
 */
public interface LogAPI {

    void log(String message, LogLevel logLevel);

    void log(String message, Object[] objects, LogLevel logLevel);

    /**
     * 得到全局日志等级
     *
     * @return
     */
    LogLevel getRootLogLevel();

    /**
     * 得到自定义包的日志等级
     *
     * @return
     */
    Map<String, LogLevel> getCustomLogLevel();
}
