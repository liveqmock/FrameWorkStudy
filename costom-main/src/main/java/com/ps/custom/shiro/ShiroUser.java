package com.ps.custom.shiro;

import com.ps.custom.entity.main.DataControl;
import com.ps.custom.entity.main.Module;
import com.ps.custom.entity.main.User;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package com.ps.custom.shiro
 * @Description 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
 * @Date 14-3-1
 * @USER saxisuer
 */
public class ShiroUser implements Serializable {
    private static final long serialVersionUID = -1748602382963711884L;
    private Long id;
    private String loginName;
    private String ipAddress;
    private User user;

    private Map<String, DataControl> hasDataControls = new HashMap<String, DataControl>();
    private Map<String, Module> hasModules = new HashMap<String, Module>();

    /**
     * 加入更多的自定义参数
     */
    private Map<String, Object> attribute = new HashMap<String, Object>();

    public ShiroUser() {
    }

    public ShiroUser(String loginName) {
        this.loginName = loginName;
    }

    public ShiroUser(Long id, String loginName) {
        this.id = id;
        this.loginName = loginName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Map<String, DataControl> getHasDataControls() {
        return hasDataControls;
    }

    public void setHasDataControls(Map<String, DataControl> hasDataControls) {
        this.hasDataControls = hasDataControls;
    }

    public Map<String, Module> getHasModules() {
        return hasModules;
    }

    public void setHasModules(Map<String, Module> hasModules) {
        this.hasModules = hasModules;
    }

    public Object getAttribute(String name) {
        return attribute.get(name);
    }

    public Map<String, Object> getAttributes() {
        return attribute;
    }

    public Object removeAttribute(String name) {
        return attribute.remove(name);
    }

    public void setAttribute(String name, Object value) {
        attribute.put(name, value);
    }
}
