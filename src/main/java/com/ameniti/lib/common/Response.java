package com.ameniti.lib.common;

/**
 * Created by manu on 12/03/2018.
 */
public class Response {

    private final Object resp;
    private final String code;
    private final String desc;

    public Response(Object resp, String code, String desc) {
        this.resp = resp;
        this.code = code;
        this.desc = desc;
    }

    public Object getResponse() {
        return resp;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return "{\"response\": \"" + resp + "\", \"code\": \"" + code + "\", \"desc\": \"" + desc + "\"}";
    }

}
