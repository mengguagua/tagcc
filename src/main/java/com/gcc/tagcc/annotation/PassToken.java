package com.gcc.tagcc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Target 用于设定注解使用范围
 * METHOD	可用于方法上
 * TYPE	可用于类或者接口上
 *
 * @Retention 可以用来修饰注解，是注解的注解，称为元注解
 * 可理解为Rentention 搭配 RententionPolicy使用。RetentionPolicy有3个值：CLASS  RUNTIME   SOURCE
 * 按生命周期来划分可分为3类：
 * 1、RetentionPolicy.SOURCE：注解只保留在源文件，当Java文件编译成class文件的时候，注解被遗弃；
 * 2、RetentionPolicy.CLASS：注解被保留到class文件，但jvm加载class文件时候被遗弃，这是默认的生命周期；
 * 3、RetentionPolicy.RUNTIME：注解不仅被保存到class文件中，jvm加载class文件之后，仍然存在；
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PassToken {
    boolean required() default true;
}
