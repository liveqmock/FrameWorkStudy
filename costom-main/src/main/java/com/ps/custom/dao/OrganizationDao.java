package com.ps.custom.dao;

import com.ps.custom.entity.main.Organization;
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
public interface OrganizationDao extends JpaRepository<Organization, Long>, JpaSpecificationExecutor<Organization> {
    @QueryHints(value = {
            @QueryHint(name = "org.hibernate.cacheable", value = "true"),
            @QueryHint(name = "org.hibernate.cacheRegion", value = "com.ps.custom.entity.main.Organization")
    })
    @Query("from Organization o order by o.priority ASC")
    List<Organization> findAllWithCache();

    Organization getByName(String name);
}
