package com.ps.custom.service;

import com.ps.custom.entity.main.OrganizationRole;
import com.ps.custom.util.dwz.Page;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * @Package com.ps.custom.service
 * @Description
 * @Date 14-3-1
 * @USER saxisuer
 */
public interface OrganizationRoleService {
    OrganizationRole get(Long id);

    void saveOrUpdate(OrganizationRole organizationRole);

    void delete(Long id);

    List<OrganizationRole> findAll(Page page);

    List<OrganizationRole> findByExample(Specification<OrganizationRole> specification, Page page);

    /**
     * 根据organizationId，找到已分配的角色。
     * 描述
     *
     * @param organizationId
     * @return
     */
    List<OrganizationRole> findByOrganizationId(Long organizationId);
}
