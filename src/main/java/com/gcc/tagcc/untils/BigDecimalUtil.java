package com.gcc.tagcc.untils;

import java.math.BigDecimal;

/**
 * @author gaocc
 * @create 2019-11-27 17:24
 */
public class BigDecimalUtil {

    public static BigDecimal createBigDecimal(String val){
        if (val == null || val.isEmpty()){
            return null;
        } else {
           return new BigDecimal(val);
        }
    }

}
