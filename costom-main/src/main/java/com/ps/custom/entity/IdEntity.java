package com.ps.custom.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * @Package com.ps.custom.entity
 * @Description 统一定义id的entity基类.
 * 基类统一定义id的属性名称、数据类型、列名映射及生成策略.
 * 子类可重载getId()函数重定义id的列名映射和生成策略.
 * @Date 14-3-1
 * @USER saxisuer
 */
//JPA 基类的标识
@MappedSuperclass
public abstract class IdEntity implements Serializable {
    /**
     * 描述
     */
    private static final long serialVersionUID = 8430941165882152228L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;


    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj, false);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, false);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
