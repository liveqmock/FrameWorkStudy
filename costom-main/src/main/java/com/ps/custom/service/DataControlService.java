package com.ps.custom.service;

import com.ps.custom.entity.main.DataControl;
import com.ps.custom.util.dwz.Page;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * @Package com.ps.custom.service
 * @Description
 * @Date 14-3-2
 * @USER saxisuer
 */
public interface DataControlService {
    DataControl get(Long id);

    void saveOrUpdate(DataControl dataControl);

    void delete(Long id);

    List<DataControl> findAll(Page page);

    List<DataControl> findByExample(Specification<DataControl> specification, Page page);
}
