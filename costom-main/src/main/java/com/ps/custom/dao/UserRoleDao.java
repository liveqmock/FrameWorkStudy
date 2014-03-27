package com.ps.custom.dao;

import com.ps.custom.entity.main.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @Package com.ps.custom.dao
 * @Description
 * @Date 14-3-1
 * @USER saxisuer
 */
public interface UserRoleDao extends JpaRepository<UserRole,Long>,JpaSpecificationExecutor<UserRole> {
    List<UserRole> findByUserId(Long userId);
}
