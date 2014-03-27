package com.ps.custom.dao;

import com.ps.custom.entity.main.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Package com.ps.custom.dao
 * @Description
 * @Date 14-3-1
 * @USER saxisuer
 */
public interface RoleDao extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {
}
