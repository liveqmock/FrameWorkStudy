package com.ps.custom.service.impl;

import com.ps.custom.dao.OrganizationRoleDao;
import com.ps.custom.entity.main.OrganizationRole;
import com.ps.custom.service.OrganizationRoleService;
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
 * @Date 14-3-1
 * @USER saxisuer
 */
@Service
@Transactional
public class OrganizationRoleServiceImpl implements OrganizationRoleService {

    @Autowired
    OrganizationRoleDao organizationRoleDao;

    @Override
    public OrganizationRole get(Long id) {
        return organizationRoleDao.findOne(id);
    }

    @Override
    public void saveOrUpdate(OrganizationRole organizationRole) {
        organizationRoleDao.save(organizationRole);
    }

    @Override
    public void delete(Long id) {
        organizationRoleDao.delete(id);
    }

    @Override
    public List<OrganizationRole> findAll(Page page) {
        org.springframework.data.domain.Page<OrganizationRole> springDataPage = organizationRoleDao.findAll(PageUtils.createPageable(page));
        page.setTotalCount(springDataPage.getTotalElements());
        return springDataPage.getContent();
    }

    @Override
    public List<OrganizationRole> findByExample(Specification<OrganizationRole> specification, Page page) {
        org.springframework.data.domain.Page<OrganizationRole> springDataPage = organizationRoleDao.findAll(PageUtils.createPageable(page));
        page.setTotalCount(springDataPage.getTotalElements());
        return springDataPage.getContent();
    }

    @Override
    public List<OrganizationRole> findByOrganizationId(Long organizationId) {
        return organizationRoleDao.findByOrganizationId(organizationId);
    }
}
