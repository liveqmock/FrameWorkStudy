package com.ps.custom.service;

import com.ps.custom.entity.main.Module;
import com.ps.custom.util.dwz.Page;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * @Package com.ps.custom.service
 * @Description
 * @Date 14-3-2
 * @USER saxisuer
 */
public interface ModuleService {
    Module get(Long id);

    void saveOrUpdate(Module module);

    void delete(Long id);

    List<Module> findAll(Page page);

    List<Module> findByExample(Specification<Module> specification, Page page);

    Module getTree();

    List<Module> findAll();
}
