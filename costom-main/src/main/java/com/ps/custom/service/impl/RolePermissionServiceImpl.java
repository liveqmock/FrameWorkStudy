package com.ps.custom.service.impl;

import com.ps.custom.dao.RolePermissionDao;
import com.ps.custom.entity.main.RolePermission;
import com.ps.custom.service.RolePermissionService;
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
public class RolePermissionServiceImpl implements RolePermissionService {
    @Autowired
    private RolePermissionDao rolePermissionDao;
    @Override
    public RolePermission get(Long id) {
        return rolePermissionDao.findOne(id);
    }

    @Override
    public void saveOrUpdate(RolePermission rolePermission) {
        rolePermissionDao.save(rolePermission);
    }

    @Override
    public void delete(Long id) {
        rolePermissionDao.delete(id);
    }

    @Override
    public List<RolePermission> findAll(Page page) {
        org.springframework.data.domain.Page<RolePermission> springDataPage = rolePermissionDao.findAll(PageUtils.createPageable(page));
        page.setTotalCount(springDataPage.getTotalElements());
        return springDataPage.getContent();
    }

    @Override
    public List<RolePermission> findByExample(Specification<RolePermission> specification, Page page) {
        org.springframework.data.domain.Page<RolePermission> springDataPage = rolePermissionDao.findAll(PageUtils.createPageable(page));
        page.setTotalCount(springDataPage.getTotalElements());
        return springDataPage.getContent();
    }

    @Override
    public List<RolePermission> findByRoleId(Long id) {
        return rolePermissionDao.findByRoleId(id);
    }

    @Override
    public void save(List<RolePermission> newRList) {
        rolePermissionDao.save(newRList);
    }

    @Override
    public void delete(List<RolePermission> delRList) {
        rolePermissionDao.delete(delRList);
    }
}
