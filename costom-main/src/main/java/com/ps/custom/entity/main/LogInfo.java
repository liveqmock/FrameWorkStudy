package com.ps.custom.entity.main;

import com.ps.custom.entity.Idable;
import com.ps.custom.log.LogLevel;

import javax.persistence.*;
import java.util.Date;

/**
 * @Package com.ps.custom.entity.main
 * @Description 日志
 * @Date 14-3-1
 * @USER saxisuer
 */
@Entity
@Table(name = "keta_log_info")
public class LogInfo implements Idable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 32)
    private String username;

    @Column(length = 256)
    private String message;

    @Column(length = 16)
    private String ipAddress;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date createTime;

    @Column(length = 16)
    @Enumerated(EnumType.STRING)
    private LogLevel logLevel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
