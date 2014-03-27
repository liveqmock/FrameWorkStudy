package com.ps.custom.entity.main;

import com.ps.custom.entity.Idable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Package com.ps.custom.entity.main
 * @Description 数据控制
 * @Date 14-3-1
 * @USER saxisuer
 */
@Entity
@Table(name = "keta_data_control")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "com.ps.custom.entity.main.DataControl")
public class DataControl implements Idable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Length(max = 64)
    @Column(length = 64, nullable = false, unique = true, updatable = false)
    private String name;

    @Length(max = 256)
    @Column(length = 256)
    private String description;

    @Length(max = 10240)
    @Column(length = 10240)
    private String control;

    @OneToMany(mappedBy = "dataControl", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<RolePermissionDataControl> rolePermissionDataControls = new ArrayList<RolePermissionDataControl>();

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the control
     */
    public String getControl() {
        return control;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @param control the control to set
     */
    public void setControl(String control) {
        this.control = control;
    }

    /**
     * @return the rolePermissionDataControls
     */
    public List<RolePermissionDataControl> getRolePermissionDataControls() {
        return rolePermissionDataControls;
    }

    /**
     * @param rolePermissionDataControls the rolePermissionDataControls to set
     */
    public void setRolePermissionDataControls(
            List<RolePermissionDataControl> rolePermissionDataControls) {
        this.rolePermissionDataControls = rolePermissionDataControls;
    }
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
