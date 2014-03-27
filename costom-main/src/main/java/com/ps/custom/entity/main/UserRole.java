package com.ps.custom.entity.main;

import com.ps.custom.entity.Idable;
import com.sun.istack.internal.NotNull;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;

/**
 * @Package com.ps.custom.entity.main
 * @Description
 * @Date 14-3-1
 * @USER saxisuer
 */
@Entity
@Table(name = "keta_user_role")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "com.ps.custom.entity.main.UserRole")
public class UserRole implements Idable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 值越小，优先级越高
     */
    @NotNull
    @Range(min = 1, max = 999)
    @Column(length = 3, nullable = false)
    private Integer priority = 999;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roleId")
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
