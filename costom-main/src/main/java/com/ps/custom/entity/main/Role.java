package com.ps.custom.entity.main;

import com.ps.custom.entity.Idable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Package com.ps.custom.entity.main
 * @Description 角色
 * @Date 14-3-1
 * @USER saxisuer
 */
@Entity
@Table(name = "keta_role")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "com.ps.custom.entity.main.Role")
public class Role implements Idable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Length(max = 64)
    @Column(length = 64, nullable = false, unique = true)
    private String name;

    @Length(max = 256)
    @Column(length = 256)
    private String description;

    @OneToMany(mappedBy = "role", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    @OrderBy("priority ASC")
    private List<UserRole> userRoles = new ArrayList<UserRole>();

    @OneToMany(mappedBy = "role", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    @OrderBy("priority ASC")
    private List<OrganizationRole> organizationRoles = new ArrayList<OrganizationRole>();

    @OneToMany(mappedBy = "role", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<RolePermission> rolePermissions = new ArrayList<RolePermission>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public List<OrganizationRole> getOrganizationRoles() {
        return organizationRoles;
    }

    public void setOrganizationRoles(List<OrganizationRole> organizationRoles) {
        this.organizationRoles = organizationRoles;
    }

    public List<RolePermission> getRolePermissions() {
        return rolePermissions;
    }

    public void setRolePermissions(List<RolePermission> rolePermissions) {
        this.rolePermissions = rolePermissions;
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
