package com.ps.custom.dao;

import com.ps.custom.entity.main.Module;
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
public interface ModuleDao extends JpaRepository<Module, Long>, JpaSpecificationExecutor<Module> {

    Page<Module> findByParentId(Long parentId, Pageable pageable);

    @QueryHints(value = {
            @QueryHint(name = "org.hibernate.cacheable", value = "true"),
            @QueryHint(name = "org.hibernate.cacheRegion", value = "com.ps.custom.entity.main.Module")
    }
    )
    @Query("from Module m order by m.priority ASC")
    List<Module> findAllWithCache();

    Module getBySn(String sn);
}
