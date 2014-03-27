package com.ps.custom.service;


import com.ps.custom.entity.main.UserRole;
import com.ps.custom.util.dwz.Page;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * @Package com.ps.custom.service
 * @Description
 * @Date 14-3-1
 * @USER saxisuer
 */
public interface UserRoleService {
    UserRole get(Long id);

    void saveOrUpdate(UserRole userRole);

    void delete(Long id);

    List<UserRole> findAll(Page page);

    List<UserRole> findByExample(Specification<UserRole> specification, Page page);

    List<UserRole> findByUserId(Long userId);
}
