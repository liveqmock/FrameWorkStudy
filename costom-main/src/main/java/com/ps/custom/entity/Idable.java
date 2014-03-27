package com.ps.custom.entity;

import java.io.Serializable;

/**
 * @Package com.ps.custom.entity.main
 * @Description
 * @Date 14-3-1
 * @USER saxisuer
 */
public interface Idable<T extends Serializable> {
    public T getId();

    public void setId(T id);
}
