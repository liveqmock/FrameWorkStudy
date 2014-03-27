package com.ps.custom.service.impl;

import com.ps.custom.dao.OrganizationDao;
import com.ps.custom.dao.UserDao;
import com.ps.custom.entity.main.Organization;
import com.ps.custom.exception.NotDeletedException;
import com.ps.custom.exception.NotExistedException;
import com.ps.custom.service.OrganizationService;
import com.ps.custom.util.dwz.Page;
import com.ps.custom.util.dwz.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Package com.ps.custom.service.impl
 * @Description
 * @Date 14-3-2
 * @USER saxisuer
 */
@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationDao organizationDao;
    @Autowired
    private UserDao userDao;

    @Override
    public Organization get(Long id) {
        return organizationDao.findOne(id);
    }

    @Override
    public void saveOrUpdate(Organization organization) {
        if (organization.getId() == null) {
            Organization parentOrganization = organizationDao.findOne(organization.getParent().getId());
            if (parentOrganization == null) {
                throw new NotExistedException("id=" + organization.getParent().getId() + "父组织不存在！");
            }

            if (organizationDao.getByName(organization.getName()) != null) {
                throw new NotExistedException(organization.getName() + "已存在！");
            }
        }

        organizationDao.save(organization);
    }

    @Override
    public void delete(Long id) {
        if (isRoot(id)) {
            throw new NotDeletedException("不允许删除根组织");
        }
        Organization organization = this.get(id);
        if (organization.getChildren().size() > 0) {
            throw new NotDeletedException(organization.getName() + "组织下存在子组织，不允许删除。");
        }
        if (userDao.findByOrganizationId(id).size() > 0) {
            throw new NotDeletedException(organization.getName() + "组织下存在用户，不允许删除。");
        }
        organizationDao.delete(id);
    }

    private boolean isRoot(Long id) {
        return id == 1;
    }

    @Override
    public List<Organization> findAll(Page page) {
        org.springframework.data.domain.Page<Organization> springDataPage = organizationDao.findAll(PageUtils.createPageable(page));
        page.setTotalCount(springDataPage.getTotalElements());
        return springDataPage.getContent();
    }

    @Override
    public List<Organization> findByExample(Specification<Organization> specification, Page page) {
        org.springframework.data.domain.Page<Organization> springDataPage = organizationDao.findAll(PageUtils.createPageable(page));
        page.setTotalCount(springDataPage.getTotalElements());
        return springDataPage.getContent();
    }

    @Override
    public Organization getByName(String name) {
        return organizationDao.getByName(name);
    }

    @Override
    public Organization getTree() {
        List<Organization> list = organizationDao.findAllWithCache();
        List<Organization> rootList = makeTree(list);
        return rootList.get(0);
    }

    private List<Organization> makeTree(List<Organization> list) {
        List<Organization> parent = new ArrayList<Organization>();
        for (Organization organization : list) {
            if (organization.getParent() == null) {
                organization.setChildren(new ArrayList<Organization>());
                parent.add(organization);
            }
        }
        list.removeAll(parent);
        makeChildren(parent, list);
        return parent;
    }

    /**
     * 递归 逐层找到子级元素
     *
     * @param parent
     * @param children
     */
    private void makeChildren(List<Organization> parent, List<Organization> children) {
        if (children.isEmpty()) {
            return;
        }
        List<Organization> temp = new ArrayList<Organization>();
        for (Organization o1 : parent) {
            for (Organization o2 : children) {
                o2.setChildren(new ArrayList<Organization>());
                if (o2.getParent().getId().equals(o1.getId())) {
                    o1.getChildren().add(o2);
                    temp.add(o2);
                }
            }
        }
        children.removeAll(temp);
        makeChildren(temp, children);
    }
}
