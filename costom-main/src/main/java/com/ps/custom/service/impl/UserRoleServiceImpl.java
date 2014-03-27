package com.ps.custom.service.impl;

import com.ps.custom.dao.UserRoleDao;
import com.ps.custom.entity.main.UserRole;
import com.ps.custom.service.UserRoleService;
import com.ps.custom.util.dwz.Page;
import com.ps.custom.util.dwz.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Package com.ps.custom.service.impl
 * @Description
 * @Date 14-3-3
 * @USER saxisuer
 */
@Service
@Transactional
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    public UserRole get(Long id) {
        return userRoleDao.findOne(id);
    }

    @Override
    public void saveOrUpdate(UserRole userRole) {
        userRoleDao.save(userRole);
    }

    @Override
    public void delete(Long id) {
        userRoleDao.delete(id);
    }

    @Override
    public List<UserRole> findAll(Page page) {
        org.springframework.data.domain.Page<UserRole> springDataPage = userRoleDao.findAll(PageUtils.createPageable(page));
        page.setTotalCount(springDataPage.getTotalElements());
        return springDataPage.getContent();
    }

    @Override
    public List<UserRole> findByExample(Specification<UserRole> specification, Page page) {
        org.springframework.data.domain.Page<UserRole> springDataPage = userRoleDao.findAll(PageUtils.createPageable(page));
        page.setTotalCount(springDataPage.getTotalElements());
        return springDataPage.getContent();
    }

    @Override
    public List<UserRole> findByUserId(Long userId) {
        return userRoleDao.findByUserId(userId);
    }
}
