package com.ps.custom.service;


import com.ps.custom.entity.main.RolePermissionDataControl;
import com.ps.custom.util.dwz.Page;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * @Package com.ps.custom.service
 * @Description
 * @Date 14-3-2
 * @USER saxisuer
 */
public interface RolePermissionDataControlService {
    RolePermissionDataControl get(Long id);

    void saveOrUpdate(RolePermissionDataControl rolePermissionDataControl);

    void delete(Long id);

    List<RolePermissionDataControl> findAll(Page page);

    List<RolePermissionDataControl> findByExample(Specification<RolePermissionDataControl> specification, Page page);

    /**
     * @param newRList
     */
    void save(List<RolePermissionDataControl> newRList);

    /**
     * @param delRList
     */
    void delete(List<RolePermissionDataControl> delRList);

    /**
     * @param id
     * @return
     */
    List<RolePermissionDataControl> findByRolePermissionRoleId(Long id);
}
