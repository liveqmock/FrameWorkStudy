package com.ps.custom.controller;

import com.ps.custom.entity.main.Organization;
import com.ps.custom.entity.main.Role;
import com.ps.custom.entity.main.User;
import com.ps.custom.entity.main.UserRole;
import com.ps.custom.exception.ExistedException;
import com.ps.custom.exception.ServiceException;
import com.ps.custom.log.Log;
import com.ps.custom.log.LogMessageObject;
import com.ps.custom.log.impl.LogUtils;
import com.ps.custom.service.*;
import com.ps.custom.util.dwz.AjaxObject;
import com.ps.custom.util.dwz.Page;
import com.ps.custom.util.persistence.DynamicSpecifications;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.validation.Valid;
import java.util.*;

/**
 * @Package com.ps.custom.controller
 * @Description
 * @Date 14-3-3
 * @USER saxisuer
 */
@Controller
@RequestMapping(value = "/management/security/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private OrganizationService organizationService;

    private static final String CREATE = "management/security/user/create";
    private static final String UPDATE = "management/security/user/update";
    private static final String LIST = "management/security/user/list";
    private static final String LOOK_UP_ROLE = "management/security/user/assign_user_role";
    private static final String LOOK_USER_ROLE = "management/security/user/delete_user_role";
    private static final String LOOK_ORG = "management/security/user/lookup_org";

    @RequiresPermissions("User:save")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String preCreate() {
        return CREATE;
    }

    @Log(message = "添加了{0}用户。")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public
    @ResponseBody
    String create(@Valid User user) {
        user.setCreateTime(new Date());
        try {
            userService.saveOrUpdate(user);
        } catch (ExistedException e) {
            return AjaxObject.newError("添加用户失败" + e.getMessage()).setCallbackType("").toString();
        }
        LogUtils.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{user.getUserName()}));
        return AjaxObject.newOk("添加用户成功！").toString();
    }

    @ModelAttribute("preloadUser")
    public User preload(@RequestParam(value = "id", required = false) Long id) {
        if (id != null) {
            User user = userService.get(id);
            user.setOrganization(null);
            return user;
        }
        return null;
    }

    @RequiresPermissions("User:edit:User拥有的资源")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
        User user = userService.get(id);
        map.put("user", user);
        return UPDATE;
    }

    @Log(message = "修改了{0}用户的信息。")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @RequiresPermissions("User:edit:User拥有的资源")
    public
    @ResponseBody
    String update(@Valid @ModelAttribute("preloadUser") User user) {
        userService.saveOrUpdate(user);
        LogUtils.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{user.getUserName()}));
        return AjaxObject.newOk("修改用户成功！").toString();
    }

    @Log(message = "删除了{0}用户。")
    @RequiresPermissions("User:delete:User拥有的资源")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public
    @ResponseBody
    String delete(@PathVariable Long id) {
        User user = null;
        try {
            user = userService.get(id);
            userService.delete(user.getId());
        } catch (ServiceException e) {
            return AjaxObject.newError("删除用户失败：" + e.getMessage()).setCallbackType("").toString();
        }

        LogUtils.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{user.getUserName()}));
        return AjaxObject.newOk("删除用户成功！").setCallbackType("").toString();
    }


    @Log(message = "删除了{0}用户。")
    @RequiresPermissions("User:delete:User拥有的资源")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public
    @ResponseBody
    String deleteMany(Long[] ids) {
        String[] usernames = new String[ids.length];
        try {
            for (int i = 0; i < ids.length; i++) {
                User user = userService.get(ids[i]);
                usernames[i] = user.getUserName();
                userService.delete(user.getId());
            }
        } catch (ServiceException e) {
            return AjaxObject.newError("删除用户失败：" + e.getMessage()).setCallbackType("").toString();
        }

        LogUtils.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(usernames)}));
        return AjaxObject.newOk("删除用户成功！").setCallbackType("").toString();
    }

    @RequiresPermissions("User:view:User拥有的资源")
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(ServletRequest request, Page page, Map<String, Object> map) {
        Specification<User> specification = DynamicSpecifications.bySearchFilter(request, User.class);
        List<User> users = userService.findByExample(specification, page);
        map.put("page", page);
        map.put("users", users);
        return LIST;
    }

    @Log(message = "{0}用户{1}")
    @RequiresPermissions("User:reset:User拥有的资源")
    @RequestMapping(value = "/reset/{type}/{userId}", method = RequestMethod.POST)
    @ResponseBody
    public String reset(@PathVariable String type, @PathVariable Long userId) {
        User user = userService.get(userId);
        AjaxObject ajaxObject = new AjaxObject();
        ajaxObject.setCallbackType("");
        if (type.equals("password")) {
            userService.resetPwd(user, "123456");
            ajaxObject.setMessage("密码重置成功,默认密码为123456");
        } else if (type.equals("status")) {
            if (user.getStatus().equals("enable")) {
                user.setStatus("disable");
            } else {
                user.setStatus("enable");
            }
            ajaxObject.setMessage("更新状态成功，当前为" + (user.getStatus().equals(User.STATUS_ENABLED) ? "可用" : "不可用"));
        }
        userService.saveOrUpdate(user);
        LogUtils.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{user.getUserName(), ajaxObject}));
        return ajaxObject.toString();
    }

    @Log(message = "向{0}用户分配了{1}的角色。")
    @RequestMapping(value = "/create/userRole", method = {RequestMethod.POST})
    @ResponseBody
    @RequiresPermissions("User:assign")
    public void assignRole(UserRole userRole) {
        userRoleService.saveOrUpdate(userRole);
        User user = userService.get(userRole.getUser().getId());
        Role role = roleService.get(userRole.getRole().getId());
        LogUtils.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{user.getUserName(), role.getName()}));
    }

    @RequiresPermissions("User:assign")
    @RequestMapping(value = "/lookup2create/userRole/{userId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String listUnassignRole(Map<String, Object> map, @PathVariable Long userId) {
        Page page = new Page();
        page.setNumPerPage(Integer.MAX_VALUE);
        List<UserRole> userRoles = userRoleService.findByUserId(userId);
        List<Role> roles = roleService.findAll(page);
        List<Role> rentList = new ArrayList<Role>();
        for (Role role : roles) {
            boolean isHas = false;
            for (UserRole userRole : userRoles) {
                if (userRole.getRole().getId().equals(role.getId())) {
                    isHas = true;
                    break;
                }
            }
            if (isHas) {
                rentList.add(role);
            }
        }
        map.put("userRoles", userRoles);
        map.put("roles", rentList);
        map.put("userId", userId);
        return LOOK_UP_ROLE;
    }

    @RequiresPermissions("User:assign")
    @RequestMapping(value = "/lookup2delete/userRole/{userId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String listUserRole(Map<String, Object> map, @PathVariable Long userId) {
        List<UserRole> userRoles = userRoleService.findByUserId(userId);
        map.put("userRoles", userRoles);
        return LOOK_USER_ROLE;
    }

    @Log(message = "撤销了{0}用户的{1}角色。")
    @RequiresPermissions("User:assign")
    @RequestMapping(value = "/delete/userRole/{userRoleId}", method = {RequestMethod.POST})
    @ResponseBody
    public void deleteUserRole(@PathVariable Long userRoleId) {
        UserRole userRole = userRoleService.get(userRoleId);
        LogUtils.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{userRole.getUser().getUserName(), userRole.getRole().getName()}));
        userRoleService.delete(userRoleId);
    }

    @RequiresPermissions(value = {"User:edit", "User:save"}, logical = Logical.OR)
    @RequestMapping(value = "/lookup2org", method = {RequestMethod.GET})
    public String lookup(Map<String, Object> map) {
        Organization org = organizationService.getTree();

        map.put("org", org);
        return LOOK_ORG;
    }
}
