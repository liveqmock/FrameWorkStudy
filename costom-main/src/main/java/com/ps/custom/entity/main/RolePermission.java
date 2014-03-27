package com.ps.custom.entity.main;

import com.ps.custom.entity.Idable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Package com.ps.custom.entity.main
 * @Description 角色权限
 * @Date 14-3-1
 * @USER saxisuer
 */
@Entity
@Table(name = "keta_role_permission")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "com.ps.custom.entity.main.RolePermission")
public class RolePermission implements Idable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roleId")
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permissionId")
    private Permission permission;

    @OneToMany(mappedBy = "rolePermission", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<RolePermissionDataControl> rolePermissionDataControls = new ArrayList<RolePermissionDataControl>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public List<RolePermissionDataControl> getRolePermissionDataControls() {
        return rolePermissionDataControls;
    }

    public void setRolePermissionDataControls(
            List<RolePermissionDataControl> rolePermissionDataControls) {
        this.rolePermissionDataControls = rolePermissionDataControls;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj, false);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, false);
    }
}

