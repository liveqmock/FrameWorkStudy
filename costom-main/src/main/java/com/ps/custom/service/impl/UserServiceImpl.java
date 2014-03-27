package com.ps.custom.service.impl;

import com.ps.custom.dao.UserDao;
import com.ps.custom.entity.main.User;
import com.ps.custom.exception.ExistedException;
import com.ps.custom.exception.IncorrectPasswordException;
import com.ps.custom.exception.NotDeletedException;
import com.ps.custom.service.UserService;
import com.ps.custom.shiro.ShiroDbRealm;
import com.ps.custom.util.dwz.Page;
import com.ps.custom.util.dwz.PageUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Package com.ps.custom.service.impl
 * @Description
 * @Date 14-3-1
 * @USER saxisuer
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private ShiroDbRealm shiroDbRealm;

    @Override
    public User get(Long id) {
        return userDao.findOne(id);
    }

    @Override
    public void saveOrUpdate(User user) {
        if (user.getId() == null) {
            if (userDao.getByUserName(user.getUserName()) != null) {
                throw new ExistedException("登录名：" + user.getUserName() + "已存在。");
            }
        }
        //设定安全的密码，使用passwordService提供的salt并经过1024次 sha-1 hash
        if (StringUtils.isNotBlank(user.getPlainPassword()) && shiroDbRealm != null) {
            ShiroDbRealm.HashPassword hashPassword = ShiroDbRealm.encryptPassword(user.getPlainPassword());
            user.setSalt(hashPassword.salt);
            user.setPassword(hashPassword.password);
        }
        userDao.save(user);
        shiroDbRealm.clearCachedAuthorizationInfo(user.getUserName());
    }

    @Override
    public void delete(Long id) {
        if (isSupervisor(id)) {
            logger.warn("操作员{}，尝试删除超级管理员用户", SecurityUtils.getSubject()
                    .getPrincipal() + "。");
            throw new NotDeletedException("不能删除超级管理员用户。");
        }
        User user = userDao.findOne(id);
        userDao.delete(user.getId());
        shiroDbRealm.clearCachedAuthorizationInfo(user.getUserName());
    }

    @Override
    public List<User> findAll(Page page) {
        org.springframework.data.domain.Page<User> springDataPage = userDao.findAll(PageUtils.createPageable(page));
        page.setTotalCount(springDataPage.getTotalElements());
        return springDataPage.getContent();
    }

    @Override
    public List<User> findByExample(Specification<User> specification, Page page) {
        org.springframework.data.domain.Page<User> springDataPage = userDao.findAll(PageUtils.createPageable(page));
        page.setTotalCount(springDataPage.getTotalElements());
        return springDataPage.getContent();
    }

    @Override
    public void updatePwd(User user, String newPwd) {
        //设定安全的密码，使用passwordService提供的salt并经过1024次 sha-1 hash
        boolean isMatch = ShiroDbRealm.validatePassword(user.getPlainPassword(), user.getPassword(), user.getSalt());
        if (isMatch) {
            ShiroDbRealm.HashPassword hashPassword = ShiroDbRealm.encryptPassword(newPwd);
            user.setSalt(hashPassword.salt);
            user.setPassword(hashPassword.password);
            userDao.save(user);
            shiroDbRealm.clearCachedAuthorizationInfo(user.getUserName());
            return;
        }
        throw new IncorrectPasswordException("用户密码错误！");
    }

    @Override
    public void resetPwd(User user, String newPwd) {
        if (newPwd == null) {
            newPwd = "11111111";
        }
        ShiroDbRealm.HashPassword hashPassword = ShiroDbRealm.encryptPassword(newPwd);
        user.setSalt(hashPassword.salt);
        user.setPassword(hashPassword.password);
        userDao.save(user);
    }

    @Override
    public User getByUsername(String username) {
        return userDao.getByUserName(username);
    }

    /**
     * 判断是否超级管理员
     *
     * @param id
     * @return
     */
    private boolean isSupervisor(Long id) {
        return id == 1;
    }
}
