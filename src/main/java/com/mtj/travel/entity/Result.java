package com.mtj.travel.entity;

import lombok.Data;

@Data
public class Result {
    private String code;
    private Object data;
    private String msg;

    public Result(String Code, Object data, String msg) {
        this.code = Code;
        this.data = data;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "{" + "code:'" + code + '\'' + ", data:" + data + ", msg:'" + msg + '\'' + '}';
    }
}
