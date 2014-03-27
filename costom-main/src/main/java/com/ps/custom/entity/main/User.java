package com.ps.custom.entity.main;

import com.ps.custom.entity.Idable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Package com.ps.custom.entity.main
 * @Description
 * @Date 14-3-1
 * @USER saxisuer
 */
@Entity
@Table(name = "keta_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "com.ps.custom.entity.main.User")
public class User implements Idable<Long> {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public static final String STATUS_DISABLED = "disabled";
    public static final String STATUS_ENABLED = "enabled";
    @NotBlank
    @Length(max = 32)
    @Column(length = 32, nullable = false)
    private String realName;

    @NotBlank
    @Length(max = 32)
    @Column(length = 32, nullable = false, unique = true, updatable = false)
    private String userName;

    @Column(length = 64, nullable = false)
    private String password;

    @Transient
    private String plainPassword;

    @Column(length = 32,nullable = false)
    private String salt;

    @Length(max=32)
    @Column(length=32)
    private String phone;

    @Email
    @Length(max=128)
    @Column(length=128)
    private String email;

    /** 使用 状态 disabled enabled */
    @NotBlank
    @Length(max=16)
    @Column(length=16, nullable=false)
    private String status = STATUS_ENABLED;

    /**
     * 帐号创建时间
     */
    @Temporal(TemporalType.TIMESTAMP) //显示日期与时间
    @Column(updatable=false)
    private Date createTime;

    @OneToMany(mappedBy="user", cascade={CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval=true)
    @OrderBy("priority ASC")
    private List<UserRole> userRoles = new ArrayList<UserRole>();

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="organizationId")
    private Organization organization;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
