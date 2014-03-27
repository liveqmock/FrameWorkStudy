package com.ps.custom.entity.main;

import com.ps.custom.entity.Idable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * @Package com.ps.custom.entity.main
 * @Description
 * @Date 14-3-1
 * @USER saxisuer
 */
@Entity
@Table(name="keta_role_permission_data_control")
@Cache(usage= CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.ps.custom.entity.main.RolePermissionDataControl")
public class RolePermissionDataControl implements Idable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="rolePermissionId")
    private RolePermission rolePermission;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="dataControlId")
    private DataControl dataControl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the rolePermission
     */
    public RolePermission getRolePermission() {
        return rolePermission;
    }

    /**
     * @param rolePermission the rolePermission to set
     */
    public void setRolePermission(RolePermission rolePermission) {
        this.rolePermission = rolePermission;
    }

    /**
     * @return the dataControl
     */
    public DataControl getDataControl() {
        return dataControl;
    }

    /**
     * @param dataControl the dataControl to set
     */
    public void setDataControl(DataControl dataControl) {
        this.dataControl = dataControl;
    }
}
