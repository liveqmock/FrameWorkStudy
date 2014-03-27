package com.ps.custom.controller;

import com.ps.custom.entity.main.Module;
import com.ps.custom.entity.main.Permission;
import com.ps.custom.exception.ExistedException;
import com.ps.custom.exception.ServiceException;
import com.ps.custom.log.Log;
import com.ps.custom.log.LogMessageObject;
import com.ps.custom.log.impl.LogUtils;
import com.ps.custom.service.ModuleService;
import com.ps.custom.service.PermissionService;
import com.ps.custom.util.dwz.AjaxObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Package com.ps.custom.controller
 * @Description
 * @Date 14-3-17
 * @USER saxisuer
 */
@Controller
@RequestMapping("/management/security/module")
public class ModuleController {

    @Autowired
    private ModuleService moduleService;
    @Autowired
    private PermissionService permissionService;

    private static final String CREATE = "management/security/module/create";
    private static final String UPDATE = "management/security/module/update";
    private static final String LIST = "management/security/module/list";
    private static final String TREE = "management/security/module/tree";
    private static final String VIEW = "management/security/module/view";
    private static final String TREE_LIST = "management/security/module/tree_list";
    private static final String LOOKUP_PARENT = "management/security/module/lookup_parent";

    @RequiresPermissions("Module:save")
    @RequestMapping(value = "/create/{parentModuleId}")
    public String preCreate(Map<String, Object> map, @PathVariable Long parentModuleId) {
        map.put("parentModuleId", parentModuleId);
        return CREATE;
    }

    @ResponseBody
    @Log(message = "添加了{0}模块。")
    @RequiresPermissions("Module:save")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid Module module) {
        Module parentModule = moduleService.get(module.getParent().getId());
        if (parentModule == null) {
            return AjaxObject.newError("添加模块失败：id=" + module.getParent().getId() + "不存在").toString();
        }
        List<Permission> permissionList = new ArrayList<Permission>();
        for (Permission permission : module.getPermissions()) {
            if (StringUtils.isNotBlank(permission.getSn())) {
                permissionList.add(permission);
            }
        }
        for (Permission permission : permissionList) {
            permission.setModule(module);
        }
        module.setPermissions(permissionList);

        try {
            moduleService.saveOrUpdate(module);
        } catch (ExistedException e) {
            return AjaxObject.newError("添加模块失败：" + e.getMessage()).toString();
        }
        LogUtils.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{module.getName()}));
        return AjaxObject.newOk("添加模块成功！").toString();
    }

    @RequiresPermissions("Module:edit")
    @RequestMapping(value = "/update/{id}")
    public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
        Module module = moduleService.get(id);
        map.put("module", module);
        return UPDATE;
    }

    @ResponseBody
    @Log(message = "修改了{0}模块的信息。")
    @RequiresPermissions("Module:edit")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@Valid Module module) {
        Module oldModule = moduleService.get(module.getId());
        oldModule.setName(module.getName());
        oldModule.setSn(module.getSn());
        oldModule.setUrl(module.getUrl());
        oldModule.setDescription(module.getDescription());
        oldModule.setParent(module.getParent());
        oldModule.setClassName(module.getClassName());
        // 模块自定义权限，删除过后新增报错，会有validate的验证错误。hibernate不能copy到permission的值，导致。
        for (Permission permission : module.getPermissions()) {
            if (StringUtils.isNotBlank(permission.getSn())) {
                if (permission.getId() == null) { // new permission
                    permission.setModule(oldModule);
                    oldModule.getPermissions().add(permission);
                    permissionService.saveOrUpdate(permission);
                }
            } else {
                if (permission.getId() != null) {
                    for (Permission oldPermission : oldModule.getPermissions()) {
                        if (oldPermission.getId().equals(permission.getId())) {
                            oldPermission.setModule(null);
                            permission = oldPermission;
                            break;
                        }
                    }
                    permissionService.delete(permission.getId());
                    oldModule.getPermissions().remove(permission);
                }
            }
        }
        moduleService.saveOrUpdate(oldModule);
        LogUtils.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{oldModule.getName()}));
        return AjaxObject.newOk("修改模块成功").toString();
    }

    @Log(message="删除了{0}模块。")
    @ResponseBody
    @RequiresPermissions("module:delete")
    @RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
    public String delete(@PathVariable Long id) {
        Module module = moduleService.get(id);
        try {
            moduleService.delete(id);
        } catch (ServiceException e) {
            return AjaxObject.newError("删除模块失败" + e.getMessage()).setCallbackType("").toString();
        }
        LogUtils.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{module.getName()}));
        return AjaxObject.newOk("删除模块成功！").setCallbackType("").toString();
    }

}
