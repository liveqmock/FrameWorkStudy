package com.ps.custom.log;


import java.lang.annotation.*;

/**
 * @Package com.ps.custom.log
 * @Description
 * @Date 14-3-3
 * @USER saxisuer
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {

    /**
     * 日志信息
     *
     * @return
     */
    String message();

    /**
     * 日志记录等级
     *
     * @return
     */
    LogLevel level() default LogLevel.TRACE;

    /**
     * 是否覆盖包日志等级
     * 1.为false不会参考level属性。
     * 2.为true会参考level属性。
     *
     * @return
     */
    boolean override() default false;
}
