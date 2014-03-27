package com.ps.custom.dao;

import com.ps.custom.entity.main.OrganizationRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;

/**
 * @Package com.ps.custom.dao
 * @Description
 * @Date 14-3-1
 * @USER saxisuer
 */
public interface OrganizationRoleDao extends JpaRepository<OrganizationRole, Long>, JpaSpecificationExecutor<OrganizationRole> {
    List<OrganizationRole> findByOrganizationId(Long organizationId);
}
