package com.pino.webChat.exception;


import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class R {
	private Boolean success;
	private Integer status;
	private String message;
	
	private Map<String, Object> data = new HashMap<> ();
	
	private R() {}

	
	public static R ok() {
        R r = new R();
        r.setSuccess(ResultCodeEnum.SUCCESS.getSuccess());
        r.setStatus(ResultCodeEnum.SUCCESS.getCode());
        r.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        return r;
    }
	
	public static R error() {
        R r = new R();
        r.setSuccess(ResultCodeEnum.ERROR.getSuccess());
        r.setStatus(ResultCodeEnum.ERROR.getCode());
        r.setMessage(ResultCodeEnum.ERROR.getMessage());
        return r;
    }


    public static R setResult(ResultCodeEnum result) {
        R r = new R();
        r.setSuccess(result.getSuccess());
        r.setStatus(result.getCode());
        r.setMessage(result.getMessage());
        return r;
    }

   
    
    //**************************
    public R data(Map<String,Object> map) {
        this.setData(map);
        return this;
    }

    public R data(String key,Object value) {
        this.data.put(key, value);
        return this;
    }

    
    public R message(String message) {
        this.setMessage(message);
        return this;
    }

    
    public R code(Integer code) {
        this.setStatus(code);
        return this;
    }

    
    public R success(Boolean success) {
        this.setSuccess(success);
        return this;
    }
}

