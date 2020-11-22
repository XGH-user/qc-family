package com.qcstudio.qcfamilyuser.exceptions;

/**
 * @author xgh
 * 用户已存在异常
 */
public class UserExistException extends RuntimeException {

    public UserExistException(){

    }

    public UserExistException(String msg){
        super(msg);
    }
}
