package com.ps.custom.log.impl;

import com.ps.custom.entity.main.LogInfo;
import com.ps.custom.log.LogLevel;
import com.ps.custom.service.LogInfoService;
import com.ps.custom.shiro.ShiroUser;
import com.ps.utils.SecurityUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package com.ps.custom.log.impl
 * @Description 全局日志等级 < 包日志等级 < 类和方法日志等级
 * @Date 14-3-3
 * @USER saxisuer
 */
public class Log4JDBCImpl extends LogAdapter {
    private LogLevel rootLogLevel = LogLevel.ERROR;

    private LogInfoService logInfoService;

    private Map<String, LogLevel> customLogLevel = new HashMap<String, LogLevel>();

    @Override
    public void log(String message, Object[] objects, LogLevel logLevel) {
        MessageFormat messageFormat = new MessageFormat(message);
        String result = messageFormat.format(objects);
        if (!StringUtils.isNotBlank(result)) {
            return;
        }
        ShiroUser shiroUser = SecurityUtils.getShiroUser();
        LogInfo logInfo = new LogInfo();
        logInfo.setCreateTime(new Date());
        logInfo.setUsername(shiroUser.getLoginName());
        logInfo.setMessage(result);
        logInfo.setIpAddress(shiroUser.getIpAddress());
        logInfo.setLogLevel(logLevel);

        logInfoService.saveOrUpdate(logInfo);
    }

    @Override
    public LogLevel getRootLogLevel() {
        return rootLogLevel;
    }

    public void setCustomLogLevel(Map<String, LogLevel> customLogLevel) {
        this.customLogLevel = customLogLevel;
    }

    @Override
    public Map<String, LogLevel> getCustomLogLevel() {
        return customLogLevel;
    }

    public void setLogInfoService(LogInfoService logInfoService) {
        this.logInfoService = logInfoService;
    }
}
