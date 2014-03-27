package com.ps.custom.shiro;

import com.ps.custom.entity.main.*;
import com.ps.custom.service.OrganizationRoleService;
import com.ps.custom.service.RoleService;
import com.ps.custom.service.UserRoleService;
import com.ps.custom.service.UserService;
import com.ps.custom.util.dwz.Page;
import com.ps.utils.Digests;
import com.ps.utils.Encodes;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @Package com.ps.custom.shiro
 * @Description
 * @Date 14-3-1
 * @USER saxisuer
 */
public class ShiroDbRealm extends AuthorizingRealm {

    private static final Logger log = LoggerFactory.getLogger(ShiroDbRealm.class);

    private static final int INTERATIONS = 1024;
    private static final int SALT_SIZE = 8;
    private static final String ALGORITHM = "SHA-1";

    // 是否启用超级管理员
    protected boolean activeRoot = false;

    // 是否使用验证码
    protected boolean useCaptcha = false;

    protected UserService userService;

    protected RoleService roleService;

    protected UserRoleService userRoleService;

    protected OrganizationRoleService organizationRoleService;


    /**
     * 给ShiroDbRealm提供编码信息，用于密码密码比对
     * 描述
     */
    public ShiroDbRealm() {
        super();
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(ALGORITHM);
        matcher.setHashIterations(INTERATIONS);

        setCredentialsMatcher(matcher);
    }


    /**
     * /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Collection<?> collection = principalCollection.fromRealm(getName());
        if (collection == null || collection.isEmpty()) {
            return null;
        }
        ShiroUser shiroUser = (ShiroUser) collection.iterator().next();
        // 设置、更新User
        shiroUser.setUser(userService.get(shiroUser.getId()));
        return newAuthorizationInfo(shiroUser);
    }

    private AuthorizationInfo newAuthorizationInfo(ShiroUser shiroUser) {
        Collection<String> hasPermissions = null;
        Collection<String> hasRoles = null;
        // 是否启用超级管理员 && id==1为超级管理员，构造所有权限
        if (activeRoot && shiroUser.getId() == 1) {
            hasRoles = new HashSet<String>();
            Page page = new Page();
            page.setNumPerPage(Integer.MAX_VALUE);
            List<Role> roles = roleService.findAll(page);
            for (Role role : roles) {
                hasRoles.add(role.getName());
            }
            hasPermissions = new HashSet<String>();
            hasPermissions.add("*");
            if (log.isInfoEnabled()) {
                log.info("使用了" + shiroUser.getLoginName() + "的超级管理员权限:" + "。At " + new Date());
                log.info(shiroUser.getLoginName() + "拥有的角色:" + hasRoles);
                log.info(shiroUser.getLoginName() + "拥有的权限:" + hasPermissions);
            }
        } else { // 普通用户 获取权限
            List<UserRole> userRoles = userRoleService.findByUserId(shiroUser.getId());
            List<OrganizationRole> organizationRoles = organizationRoleService
                    .findByOrganizationId(shiroUser.getUser().getOrganization().getId());

            Collection<Role> roles = getUserRoles(userRoles, organizationRoles);
            hasRoles = makeRoles(roles, shiroUser);
            hasPermissions = makePermissions(roles, shiroUser);
        }
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRoles(hasRoles);
        simpleAuthorizationInfo.addStringPermissions(hasPermissions);
        return simpleAuthorizationInfo;
    }

    /**
     * 组装资源操作权限
     *
     * @param roles
     * @param shiroUser
     * @return
     */
    private Collection<String> makePermissions(Collection<Role> roles, ShiroUser shiroUser) {
        // 清空shiroUser中map
        shiroUser.getHasDataControls().clear();
        shiroUser.getHasModules().clear();
        Collection<String> stringPermissions = new ArrayList<String>();
        for (Role role : roles) {
            List<RolePermission> rolePermissions = role.getRolePermissions();
            for (RolePermission rolePermission : rolePermissions) {
                Permission permission = rolePermission.getPermission();
                String resource = permission.getModule().getSn();
                String operate = permission.getSn();
                StringBuilder builder = new StringBuilder();
                builder.append(resource + ":" + operate);

                shiroUser.getHasModules().put(resource, permission.getModule());
                StringBuilder dcBuilder = new StringBuilder();
                for (RolePermissionDataControl rpdc : rolePermission.getRolePermissionDataControls()) {
                    DataControl dataControl = rpdc.getDataControl();
                    dcBuilder.append(dataControl.getName() + ",");

                    shiroUser.getHasDataControls().put(dataControl.getName(), dataControl);
                }
                if (dcBuilder.length() > 0) {
                    builder.append(":" + dcBuilder.substring(0, dcBuilder.length() - 1));
                }

                stringPermissions.add(builder.toString());
            }
        }
        if (log.isInfoEnabled()) {
            log.info(shiroUser.getLoginName() + "拥有的权限:" + stringPermissions);
        }
        return stringPermissions;
    }

    /**
     * 组装权限
     *
     * @param roles
     * @param shiroUser
     * @return
     */
    private Collection<String> makeRoles(Collection<Role> roles, ShiroUser shiroUser) {
        Collection<String> hasRoles = new HashSet<String>();
        for (Role role : roles) {
            hasRoles.add(role.getName());
        }
        if (log.isInfoEnabled()) {
            log.info(shiroUser.getLoginName() + "拥有的角色:" + hasRoles);
        }
        return hasRoles;
    }

    /**
     * 整合 角色角色 (组织角色 并 用户角色)
     *
     * @param userRoles
     * @param organizationRoles
     * @return
     */
    private Collection<Role> getUserRoles(List<UserRole> userRoles, List<OrganizationRole> organizationRoles) {
        Set<Role> roles = new HashSet<Role>();
        for (UserRole userRole : userRoles) {
            roles.add(userRole.getRole());
        }

        for (OrganizationRole organizationRole : organizationRoles) {
            roles.add(organizationRole.getRole());
        }

        return roles;
    }

    /**
     * 认证回调函数, 登录时调用.
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        if (useCaptcha) {
            CaptchaUsernamePasswordToken token = (CaptchaUsernamePasswordToken) authenticationToken;
            String param = token.getCaptcha();
            if (!PatchcaServlet.validateCode(SecurityUtils.getSubject().getSession().getId().toString(), param.toLowerCase())) {
                throw new IncorrectCaptchaException("验证码错误");
            }
        }
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user = userService.getByUsername(token.getUsername());
        if (user != null) {
            if (user.getStatus().equals(User.STATUS_DISABLED)) {
                throw new DisabledAccountException();
            }
            byte[] salt = Encodes.decodeHex(user.getSalt());
            ShiroUser shiroUser = new ShiroUser(user.getId(), user.getUserName());
            // 这里可以缓存认证
            return new SimpleAuthenticationInfo(shiroUser, user.getPassword(), ByteSource.Util.bytes(salt), getName());
        } else {
            return null;
        }
    }

    public static class HashPassword {
        public String salt;
        public String password;
    }

    /**
     * 明文加密
     *
     * @param plainPassword
     * @return
     */
    public static HashPassword encryptPassword(String plainPassword) {
        HashPassword result = new HashPassword();
        byte[] salt = Digests.generateSalt(SALT_SIZE);
        result.salt = Encodes.encodeHex(salt);

        byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, INTERATIONS);
        result.password = Encodes.encodeHex(hashPassword);
        return result;
    }

    /**
     * 验证密码
     *
     * @param plainPassword 明文密码
     * @param password      密文密码
     * @param salt          盐值
     * @return
     */
    public static boolean validatePassword(String plainPassword, String password, String salt) {
        byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), Encodes.decodeHex(salt), INTERATIONS);
        String oldPassword = Encodes.encodeHex(hashPassword);
        return password.equals(oldPassword);
    }

    /**
     * 覆盖父类方法，设置AuthorizationCacheKey为ShiroUser的loginName，这样才能顺利删除shiro中的缓存。
     * 因为shiro默认的AuthorizationCacheKey为PrincipalCollection的对象。
     *
     * @see org.apache.shiro.realm.AuthorizingRealm#getAuthorizationCacheKey(org.apache.shiro.subject.PrincipalCollection)
     */
    @Override
    protected Object getAuthorizationCacheKey(PrincipalCollection principals) {
        ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
        return shiroUser.getLoginName();
    }

    /**
     * 更新用户授权信息缓存.
     */
    public void clearCachedAuthorizationInfo(String loginName) {
        ShiroUser shiroUser = new ShiroUser(loginName);

        SimplePrincipalCollection principals = new SimplePrincipalCollection(shiroUser, getName());
        clearCachedAuthorizationInfo(principals);
    }

    /**
     * 清除所有用户授权信息缓存.
     */
    public void clearAllCachedAuthorizationInfo() {
        Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
        if (cache != null) {
            for (Object key : cache.keys()) {
                cache.remove(key);
            }
        }
    }

    public void setActiveRoot(boolean activeRoot) {
        this.activeRoot = activeRoot;
    }

    public void setUseCaptcha(boolean useCaptcha) {
        this.useCaptcha = useCaptcha;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    public void setUserRoleService(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    public void setOrganizationRoleService(
            OrganizationRoleService organizationRoleService) {
        this.organizationRoleService = organizationRoleService;
    }

    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }
}
