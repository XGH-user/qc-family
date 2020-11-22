package com.qcstudio.qcfamilyuser.exceptions;

/**
 * @author xgh
 */
public class IllegalParameterException extends RuntimeException {
    public IllegalParameterException(){

    }

    public IllegalParameterException(String msg){
        super(msg);
    }
}
