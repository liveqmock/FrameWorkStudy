package com.ps.custom.log;

/**
 * @Package com.ps.custom.log
 * @Description
 * @Date 14-3-1
 * @USER saxisuer
 */
public enum LogLevel {
    TRACE("TRACE"),

    DEBUG("DEBUG"),

    INFO("INFO"),

    WARN("WARN"),

    ERROR("ERROR");
    private String value;

    LogLevel(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
