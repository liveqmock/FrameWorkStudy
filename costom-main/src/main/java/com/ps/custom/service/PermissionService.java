package com.ps.custom.service;

import com.ps.custom.entity.main.Permission;
import com.ps.custom.util.dwz.Page;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * @Package com.ps.custom.service
 * @Description
 * @Date 14-3-2
 * @USER saxisuer
 */
public interface PermissionService {
    Permission get(Long id);

    void saveOrUpdate(Permission permission);

    void delete(Long id);

    List<Permission> findAll(Page page);

    List<Permission> findByExample(Specification<Permission> specification, Page page);
}
