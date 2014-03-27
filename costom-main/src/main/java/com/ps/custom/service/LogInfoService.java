package com.ps.custom.service;

import com.ps.custom.entity.main.LogInfo;
import com.ps.custom.util.dwz.Page;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * @Package com.ps.custom.service
 * @Description
 * @Date 14-3-2
 * @USER saxisuer
 */
public interface LogInfoService {
    LogInfo get(Long id);

    void saveOrUpdate(LogInfo logInfo);

    void delete(Long id);

    List<LogInfo> findAll(Page page);

    List<LogInfo> findByExample(Specification<LogInfo> specification, Page page);
}
