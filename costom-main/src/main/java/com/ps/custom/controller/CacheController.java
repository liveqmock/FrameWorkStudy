package com.ps.custom.controller;

import com.ps.custom.log.Log;
import com.ps.custom.service.CacheService;
import com.ps.custom.util.dwz.AjaxObject;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Package com.ps.custom.controller
 * @Description
 * @Date 14-3-14
 * @USER saxisuer
 */
@Controller
@RequestMapping(value = "/management/security/cache")
public class CacheController {
    @Autowired
    private CacheService cacheService;
    private static final String INDEX = "management/security/cache/index";

    @RequiresPermissions("Cache:view")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return INDEX;
    }

    @Log(message = "进行了缓存清理。")
    @RequiresPermissions(value = {"Cache:edit", "Cache:delete"}, logical = Logical.OR)
    @RequestMapping(value = "/clear", method = RequestMethod.POST)
    @ResponseBody
    public String clear() {
        cacheService.clearAllCache();
        return AjaxObject.newOk("清除缓存成功！").setCallbackType("").toString();
    }
}
