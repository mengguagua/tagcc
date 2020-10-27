package com.gcc.tagcc.untils;

import com.gcc.tagcc.entity.Result;

public class ResultUtil {
    public static Result success(Object object) {
        Result result = new Result();
        result.setCode(0);
        result.setMsg("success");
        result.setData(object);
        return result;
    }

    public static Result success() {
        return success(null);
    }

    public static Result error(Integer code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setData(msg);
        result.setMsg(msg);
        return result;
    }
}
