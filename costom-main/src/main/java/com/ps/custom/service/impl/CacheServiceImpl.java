package com.ps.custom.service.impl;

import com.ps.custom.service.CacheService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @Package com.ps.custom.service.impl
 * @Description
 * @Date 14-3-2
 * @USER saxisuer
 */
@Service
public class CacheServiceImpl implements CacheService {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void clearAllCache() {
        em.getEntityManagerFactory().getCache().evictAll();
    }
}
