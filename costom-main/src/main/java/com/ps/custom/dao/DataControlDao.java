package com.ps.custom.dao;

import com.ps.custom.entity.main.DataControl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Package com.ps.custom.dao
 * @Description
 * @Date 14-3-2
 * @USER saxisuer
 */
public interface DataControlDao extends JpaRepository<DataControl, Long>, JpaSpecificationExecutor<DataControl> {

}
