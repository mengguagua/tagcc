package com.gcc.tagcc.untils;

import java.util.UUID;

/**
 * Created by gcc on 2018/3/13.
 */
public class UuidUntil {

    public static String getUUID(){
        return  UUID.randomUUID().toString().replace("-","");
    }

}
