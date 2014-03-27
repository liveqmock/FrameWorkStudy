package com.ps.custom.service.impl;

import com.ps.custom.dao.LogInfoDao;
import com.ps.custom.entity.main.LogInfo;
import com.ps.custom.service.LogInfoService;
import com.ps.custom.util.dwz.Page;
import com.ps.custom.util.dwz.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Package com.ps.custom.service.impl
 * @Description
 * @Date 14-3-2
 * @USER saxisuer
 */
@Service
@Transactional
public class LogInfoServiceImpl implements LogInfoService{

    @Autowired
    private LogInfoDao logInfoDao;

    @Override
    public LogInfo get(Long id) {
        return logInfoDao.findOne(id);
    }

    @Override
    public void saveOrUpdate(LogInfo logInfo) {
        logInfoDao.save(logInfo);
    }

    @Override
    public void delete(Long id) {
        logInfoDao.delete(id);
    }

    @Override
    public List<LogInfo> findAll(Page page) {
        org.springframework.data.domain.Page<LogInfo> springDataPage = logInfoDao.findAll(PageUtils.createPageable(page));
        page.setTotalCount(springDataPage.getTotalElements());
        return springDataPage.getContent();
    }

    @Override
    public List<LogInfo> findByExample(Specification<LogInfo> specification, Page page) {
        org.springframework.data.domain.Page<LogInfo> springDataPage = logInfoDao.findAll(PageUtils.createPageable(page));
        page.setTotalCount(springDataPage.getTotalElements());
        return springDataPage.getContent();
    }
}
