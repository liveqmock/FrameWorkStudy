package com.ps.custom.dao;

import com.ps.custom.entity.main.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @Package com.ps.custom.dao
 * @Description
 * @Date 14-3-1
 * @USER saxisuer
 */
public interface UserDao extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    User getByUserName(String name);

    List<User> findByOrganizationId(Long id);
}
