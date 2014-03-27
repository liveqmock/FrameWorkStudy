package com.ps.custom.service.impl;

import com.ps.custom.dao.PermissionDao;
import com.ps.custom.entity.main.Permission;
import com.ps.custom.service.PermissionService;
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
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    @Override
    public Permission get(Long id) {
        return permissionDao.findOne(id);
    }

    @Override
    public void saveOrUpdate(Permission permission) {
        permissionDao.save(permission);
    }

    @Override
    public void delete(Long id) {
        permissionDao.delete(id);
    }

    @Override
    public List<Permission> findAll(Page page) {
        org.springframework.data.domain.Page<Permission> springDataPage = permissionDao.findAll(PageUtils.createPageable(page));
        page.setTotalCount(springDataPage.getTotalElements());
        return springDataPage.getContent();
    }

    @Override
    public List<Permission> findByExample(Specification<Permission> specification, Page page) {
        org.springframework.data.domain.Page<Permission> springDataPage = permissionDao.findAll(PageUtils.createPageable(page));
        page.setTotalCount(springDataPage.getTotalElements());
        return springDataPage.getContent();
    }
}
