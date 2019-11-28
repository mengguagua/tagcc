package com.gcc.tagcc.exception;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gaocc
 * @create 2019-11-28 15:31
 */
public class RemotingExceptionUtils {

    static final Map<String, String> allPrompt = new ConcurrentHashMap<>();

    public static void addCode(String errorCode, String errorPrompt) {
        allPrompt.put(errorCode, errorPrompt);
    }

    public static String getPrompt(String errorCode) {
        return allPrompt.get(errorCode);
    }
}