package com.ps.custom.service;

import com.ps.custom.entity.main.RolePermission;
import com.ps.custom.util.dwz.Page;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * @Package com.ps.custom.service
 * @Description
 * @Date 14-3-2
 * @USER saxisuer
 */
public interface RolePermissionService {
    RolePermission get(Long id);

    void saveOrUpdate(RolePermission rolePermission);

    void delete(Long id);

    List<RolePermission> findAll(Page page);

    List<RolePermission> findByExample(Specification<RolePermission> specification, Page page);

    /**
     * @param id
     * @return
     */
    List<RolePermission> findByRoleId(Long id);

    /**
     * @param newRList
     */
    void save(List<RolePermission> newRList);

    void delete(List<RolePermission> delRList);
}
