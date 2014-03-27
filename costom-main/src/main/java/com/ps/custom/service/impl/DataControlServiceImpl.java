package com.ps.custom.service.impl;

import com.ps.custom.dao.DataControlDao;
import com.ps.custom.entity.main.DataControl;
import com.ps.custom.service.DataControlService;
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
@Transactional
@Service
public class DataControlServiceImpl implements DataControlService {

    @Autowired
    private DataControlDao dataControlDao;

    @Override
    public DataControl get(Long id) {
        return dataControlDao.findOne(id);
    }

    @Override
    public void saveOrUpdate(DataControl dataControl) {
        dataControlDao.save(dataControl);
    }

    @Override
    public void delete(Long id) {
        dataControlDao.delete(id);
    }

    @Override
    public List<DataControl> findAll(Page page) {
        org.springframework.data.domain.Page<DataControl> springDataPage = dataControlDao.findAll(PageUtils.createPageable(page));
        page.setTotalCount(springDataPage.getTotalElements());
        return springDataPage.getContent();
    }

    @Override
    public List<DataControl> findByExample(Specification<DataControl> specification, Page page) {
        org.springframework.data.domain.Page<DataControl> springDataPage = dataControlDao.findAll(PageUtils.createPageable(page));
        return springDataPage.getContent();
    }
}
