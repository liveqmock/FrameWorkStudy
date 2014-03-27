package com.ps.custom.service;

import com.ps.custom.entity.main.Organization;
import com.ps.custom.util.dwz.Page;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * @Package com.ps.custom.service
 * @Description
 * @Date 14-3-2
 * @USER saxisuer
 */
public interface OrganizationService {

    Organization get(Long id);

    void saveOrUpdate(Organization organization);

    void delete(Long id);

    List<Organization> findAll(Page page);

    List<Organization> findByExample(Specification<Organization> specification, Page page);

    Organization getByName(String name);

    Organization getTree();
}
