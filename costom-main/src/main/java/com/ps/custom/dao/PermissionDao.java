package com.ps.custom.dao;

import com.ps.custom.entity.main.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Package com.ps.custom.dao
 * @Description
 * @Date 14-3-2
 * @USER saxisuer
 */
public interface PermissionDao extends JpaRepository<Permission, Long>, JpaSpecificationExecutor<Permission> {
}
