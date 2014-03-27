package com.ps.custom.entity.main;

import com.ps.custom.entity.Idable;
import com.sun.istack.internal.NotNull;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


/**
 * @Package com.ps.custom.entity.main
 * @Description 组织机构
 * @Date 14-3-1
 * @USER saxisuer
 */
@Entity
@Table(name = "keta_organization")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "com.ps.custom.entity.main.Organization")
public class Organization implements Idable<Long>, Comparable<Organization> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Length(max = 64)
    @Column(length = 64, nullable = false, unique = true)
    private String name;

    /**
     * 越小优先级越高
     */
    @NotNull
    @Range(min = 1, max = 999)
    @Column(length = 3, nullable = false)
    private Integer priority = 999;

    @Length(max = 256)
    @Column(length = 256)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentId")
    private Organization parent;

    @OneToMany(mappedBy = "parent", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @OrderBy("priority ASC")
    private List<Organization> children = new ArrayList<Organization>();

    @OneToMany(mappedBy = "organization", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<User> users = new ArrayList<User>();

    @OneToMany(mappedBy = "organization", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    @OrderBy("priority ASC")
    private List<OrganizationRole> organizationRoles = new ArrayList<OrganizationRole>();

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

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Organization getParent() {
        return parent;
    }

    public void setParent(Organization parent) {
        this.parent = parent;
    }

    public List<Organization> getChildren() {
        return children;
    }

    public void setChildren(List<Organization> children) {
        this.children = children;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<OrganizationRole> getOrganizationRoles() {
        return organizationRoles;
    }

    public void setOrganizationRoles(List<OrganizationRole> organizationRoles) {
        this.organizationRoles = organizationRoles;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    /** 比较部门之间的优先级大小*/
    @Override
    public int compareTo(Organization org) {
        if (org == null) {
            return -1;
        } else if (org == this) {
            return 0;
        } else if (this.priority < org.getPriority()) {
            return -1;
        } else if (this.priority > org.getPriority()) {
            return 1;
        }

        return 0;
    }
}
