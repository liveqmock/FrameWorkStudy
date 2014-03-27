package com.ps.custom.service;

import com.ps.custom.entity.main.Role;
import com.ps.custom.util.dwz.Page;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * @Package com.ps.custom.service
 * @Description
 * @Date 14-3-1
 * @USER saxisuer
 */
public interface RoleService {

    Role get(Long id);

    void saveOrUpdate(Role role);

    void delete(Long id);

    List<Role> findAll(Page page);

    List<Role> findByExample(Specification<Role> specification, Page page);
}
