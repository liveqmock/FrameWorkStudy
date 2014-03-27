package com.ps.custom.dao;

import com.ps.custom.entity.main.LogInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Package com.ps.custom.dao
 * @Description
 * @Date 14-3-2
 * @USER saxisuer
 */
public interface LogInfoDao extends JpaRepository<LogInfo, Long>, JpaSpecificationExecutor<LogInfo> {
}
