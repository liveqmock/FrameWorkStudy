package com.ps.custom.entity.main;

import com.ps.custom.entity.Idable;
import com.sun.istack.internal.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;

/**
 * @Package com.ps.custom.entity.main
 * @Description 组织机构权限
 * @Date 14-3-1
 * @USER saxisuer
 */
@Entity
@Table(name="keta_organization_role")
@Cache(usage= CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.ps.custom.entity.main.OrganizationRole")
public class OrganizationRole implements Idable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 值越小，优先级越高
     */
    @NotNull
    @Range(min=1, max=999)
    @Column(length=3, nullable=false)
    private Integer priority = 999;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="roleId")
    private Role role;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="organizationId")
    private Organization organization;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 返回 role 的值
     * @return role
     */
    public Role getRole() {
        return role;
    }

    /**
     * 设置 role 的值
     * @param role
     */
    public void setRole(Role role) {
        this.role = role;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    /**
     * 返回 priority 的值
     * @return priority
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * 设置 priority 的值
     * @param priority
     */
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

}
