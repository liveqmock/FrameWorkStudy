package com.ps.custom.service.impl;

import com.ps.custom.dao.DictionaryDao;
import com.ps.custom.entity.main.Dictionary;
import com.ps.custom.service.DictionaryService;
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
public class DictionaryServiceImpl implements DictionaryService {

    @Autowired
    private DictionaryDao dictionaryDao;

    @Override
    public Dictionary get(Long id) {
        return dictionaryDao.findOne(id);
    }

    @Override
    public void saveOrUpdate(Dictionary dictionary) {
        dictionaryDao.save(dictionary);
    }

    @Override
    public void delete(Long id) {
        dictionaryDao.delete(id);
    }

    @Override
    public List<Dictionary> findAll(Page page) {
        org.springframework.data.domain.Page<Dictionary> springDataPage = dictionaryDao.findAll(PageUtils.createPageable(page));
        page.setTotalCount(springDataPage.getTotalElements());
        return springDataPage.getContent();
    }

    @Override
    public List<Dictionary> findByExample(Specification<Dictionary> specification, Page page) {
        org.springframework.data.domain.Page<Dictionary> springDataPage = dictionaryDao.findAll(specification, PageUtils.createPageable(page));
        page.setTotalCount(springDataPage.getTotalElements());
        return springDataPage.getContent();
    }

    @Override
    public List<Dictionary> findByThemeName(String themeName, Page page) {
        if (page == null) {
            return dictionaryDao.findAllItems(themeName);
        }
        org.springframework.data.domain.Page<Dictionary> springDataPage = dictionaryDao.findByParentNameAndType(themeName, Dictionary.DictionaryType.ITEM, PageUtils.createPageable(page));
        page.setTotalCount(springDataPage.getTotalElements());
        return springDataPage.getContent();
    }
}
