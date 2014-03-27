package com.ps.custom.dao;

import com.ps.custom.entity.main.Dictionary;
import com.ps.custom.entity.main.Dictionary.DictionaryType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import java.util.List;

/**
 * @Package com.ps.custom.dao
 * @Description
 * @Date 14-3-2
 * @USER saxisuer
 */
public interface DictionaryDao extends JpaRepository<Dictionary, Long>, JpaSpecificationExecutor<Dictionary> {

    @QueryHints(value = {
            @QueryHint(name = "org.hibernate.cacheable", value = "true"),
            @QueryHint(name = "org.hibernate.cacheRegion", value = "com.ps.custom.entity.main.Dictionary")
    }
    )
    Page<Dictionary> findByParentNameAndType(String name, DictionaryType dictionaryType, Pageable pageable);

    @QueryHints(value = {
            @QueryHint(name = "org.hibernate.cacheable", value = "true"),
            @QueryHint(name = "org.hibernate.cacheRegion", value = "com.ps.custom.entity.main.Dictionary")
    }
    )
    @Query("FROM Dictionary d WHERE d.parent.name=?1 AND d.type='ITEM' ORDER BY d.priority ASC")
    List<Dictionary> findAllItems(String themeName);


}
