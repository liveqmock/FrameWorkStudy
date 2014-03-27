package com.ps.custom.log.impl;

import com.ps.custom.log.LogAPI;
import com.ps.custom.log.LogLevel;

import java.util.HashMap;
import java.util.Map;

/**
 * @Package com.ps.custom.log.impl
 * @Description
 * @Date 14-3-3
 * @USER saxisuer
 */
public class LogAdapter implements LogAPI {
    @Override
    public void log(String message, LogLevel logLevel) {
        log(message, null, logLevel);
    }

    @Override
    public void log(String message, Object[] objects, LogLevel logLevel) {

    }

    @Override
    public LogLevel getRootLogLevel() {
        return LogLevel.ERROR;
    }

    @Override
    public Map<String, LogLevel> getCustomLogLevel() {
        return new HashMap<String, LogLevel>();
    }
}
