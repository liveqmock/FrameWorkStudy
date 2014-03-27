package com.ps.custom.dao;

import com.ps.custom.entity.main.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @Package com.ps.custom.dao
 * @Description
 * @Date 14-3-2
 * @USER saxisuer
 */
public interface RolePermissionDao extends JpaRepository<RolePermission, Long>, JpaSpecificationExecutor<RolePermission> {
    /**
     * @param id
     * @return
     */
    List<RolePermission> findByRoleId(Long roleId);
}
