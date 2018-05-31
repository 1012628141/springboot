package com.zjy.springboot.utils;


import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * 统一返回接口定义
 *
 * @author
 */
public class JsonResult{

    private Integer code;
    private String msg;
    private Object data;  // 返回对象

    public JsonResult() {
    }

    public JsonResult(Integer code, Object data) {
        this.code = code;
        this.msg ="";
        this.data = data;
    }

    public JsonResult(Integer code,String message, Object data) {
        this.code = code;
        this.msg = message;
        this.data = data;
    }


    /**
     * 返回json字符串
     *
     * @param code
     * @param data
     * @return
     */
    public static String toString(Integer code, Object data) {
        return JSON.toJSONString(new JsonResult(code, data));
    }

    public static String toString(Integer code, String message,Object data) {
        return JSON.toJSONString(new JsonResult(code,message,data));
    }

    /**
     * 默认内容
     *
     * @param code
     * @param data
     * @param defMsg
     * @return
     */
    public static String toString(Integer code, String data, boolean defMsg) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(code);
        jsonResult.setMsg(data);
        return JSON.toJSONString(jsonResult);
    }

    public static String toString(Integer code,String message) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(code);
        jsonResult.setMsg(message);
        jsonResult.setData("");
        return JSON.toJSONString(jsonResult);
    }



    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String message) {
        this.msg = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}