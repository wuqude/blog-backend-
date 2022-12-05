package com.sr.blog.dao.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor

public class Result {
    private boolean success;

    private Integer code;

    private String msg;

    private Object data;

    public  static Result sucess(Object data){
        return new Result(true,200,"success",data);
    }
    public static Result fail (Object data,int code,String msg){
        return  new Result(false,code,msg,null);
    }
}
