package com.ps.custom.service.impl;

import com.ps.custom.dao.RoleDao;
import com.ps.custom.entity.main.Role;
import com.ps.custom.service.RoleService;
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
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public Role get(Long id) {
        return roleDao.findOne(id);
    }

    @Override
    public void saveOrUpdate(Role role) {
        roleDao.save(role);
    }

    @Override
    public void delete(Long id) {
        roleDao.delete(id);

    }

    @Override
    public List<Role> findAll(Page page) {
        org.springframework.data.domain.Page<Role> springDataPage = roleDao.findAll(PageUtils.createPageable(page));
        page.setTotalCount(springDataPage.getTotalElements());
        return springDataPage.getContent();
    }

    @Override
    public List<Role> findByExample(Specification<Role> specification, Page page) {
        org.springframework.data.domain.Page<Role> springDataPage = roleDao.findAll(specification, PageUtils.createPageable(page));
        page.setTotalCount(springDataPage.getTotalElements());
        return springDataPage.getContent();
    }
}
