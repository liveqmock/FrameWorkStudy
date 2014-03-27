package com.ps.custom.dao;

import com.ps.custom.entity.main.RolePermissionDataControl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @Package com.ps.custom.dao
 * @Description
 * @Date 14-3-2
 * @USER saxisuer
 */
public interface RolePermissionDataControlDao extends JpaRepository<RolePermissionDataControl, Long>, JpaSpecificationExecutor<RolePermissionDataControl> {
    List<RolePermissionDataControl> findByRolePermissionRoleId(Long id);
}
