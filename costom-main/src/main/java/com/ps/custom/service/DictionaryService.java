package com.ps.custom.service;

import com.ps.custom.entity.main.Dictionary;
import com.ps.custom.util.dwz.Page;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * @Package com.ps.custom.service
 * @Description
 * @Date 14-3-2
 * @USER saxisuer
 */
public interface DictionaryService {
    Dictionary get(Long id);

    void saveOrUpdate(Dictionary dictionary);

    void delete(Long id);

    List<Dictionary> findAll(Page page);

    List<Dictionary> findByExample(Specification<Dictionary> specification, Page page);

    List<Dictionary> findByThemeName(String themeName, Page page);
}
