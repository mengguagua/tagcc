/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/csstj/csstj">csstj</a> All rights reserved.
 */
package com.gcc.tagcc.untils.annotation;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * 标识MyBatis的DAO,方便{@link org.mybatis.spring.mapper.MapperScannerConfigurer}的扫描。 
 * @author gaocc
 * @version 2019-05-01
 */
@Component
@Mapper
public @interface MyBatisDao {
	
	/**
	 * The value may indicate a suggestion for a logical component name,
	 * to be turned into a Spring bean in case of an autodetected component.
	 * @return the suggested component name, if any
	 */
	String value() default "";

}