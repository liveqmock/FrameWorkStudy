package com.ps.custom.service.impl;

import com.ps.custom.dao.RolePermissionDataControlDao;
import com.ps.custom.entity.main.RolePermissionDataControl;
import com.ps.custom.service.RolePermissionDataControlService;
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
public class RolePermissionDataControlServiceImpl implements RolePermissionDataControlService {

    @Autowired
    private RolePermissionDataControlDao rolePermissionDataControlDao;

    @Override
    public RolePermissionDataControl get(Long id) {
        return rolePermissionDataControlDao.findOne(id);
    }

    @Override
    public void saveOrUpdate(RolePermissionDataControl rolePermissionDataControl) {
        rolePermissionDataControlDao.save(rolePermissionDataControl);
    }

    @Override
    public void delete(Long id) {
        rolePermissionDataControlDao.delete(id);
    }

    @Override
    public List<RolePermissionDataControl> findAll(Page page) {
        org.springframework.data.domain.Page<RolePermissionDataControl> springDataPage = rolePermissionDataControlDao.findAll(PageUtils.createPageable(page));
        page.setTotalCount(springDataPage.getTotalElements());
        return springDataPage.getContent();
    }

    @Override
    public List<RolePermissionDataControl> findByExample(Specification<RolePermissionDataControl> specification, Page page) {
        org.springframework.data.domain.Page<RolePermissionDataControl> springDataPage = rolePermissionDataControlDao.findAll(PageUtils.createPageable(page));
        page.setTotalCount(springDataPage.getTotalElements());
        return springDataPage.getContent();
    }

    @Override
    public void save(List<RolePermissionDataControl> newRList) {
        rolePermissionDataControlDao.save(newRList);
    }

    @Override
    public void delete(List<RolePermissionDataControl> delRList) {
        rolePermissionDataControlDao.delete(delRList);
    }

    @Override
    public List<RolePermissionDataControl> findByRolePermissionRoleId(Long id) {
        return rolePermissionDataControlDao.findByRolePermissionRoleId(id);
    }
}
