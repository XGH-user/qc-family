package com.qcstudio.qcfamilyuser.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author xgh
 * 操作码枚举类
 */

public enum CodeEnum implements IEnum<String> {
    /**
     *
     */
    SUCCESS("1000", "操作成功"),
    ERROR("2000", "操作失败"),
    INVALID_USERNAME_PASSWORD("2001", "用户名或密码错误"),
    INVALID_USER("2002", "用户不存在"),
    INVALID_USER_EXIST("2003", "用户已存在"),
    INVALID_ROLE("2004", "角色不存在"),
    INVALID_PASSWORD("2005", "密码错误"),
    USER_NO_PERMITION("2009", "当前用户无该接口权限"),
    VERIFY_PARAM_ERROR("2010", "校验码错误"),
    VERIFY_PARAM_PASS("2011", "校验码过期"),
    UPDATE_USERINFO_ERROR("2012", "更新用户信息失败"),
    DELETE_USERINFO_ERROR("2013", "删除用户信息失败"),
    INSERT_USERINFO_ERROR("2014", "保存用户信息失败"),
    QUERY_USERINFO_ERROR("2015", "查看用户信息失败"),
    UPDATE_ROLEINFO_ERROR("2016", "更新角色信息失败"),
    DELETE_ROLEINFO_ERROR("2017", "删除角色信息失败"),
    INSERT_ROLEINFO_ERROR("2018", "保存角色信息失败"),
    QUERY_ROLEINFO_ERROR("2019", "查看角色信息失败"),
    UPDATE_PERMITIOMINFO_ERROR("2020", "更新权限信息失败"),
    DELETE_PERMITIOMINFO_ERROR("2021", "删除权限信息失败"),
    INSERT_PERMITIOMINFO_ERROR("2022", "保存权限信息失败"),
    QUERY_PERMITIOMINFO_ERROR("2023", " 查看权限信息失败"),
    INVALID_USER_NOTLOGIN("2005","用户未登陆");

    private String respCode;
    @JsonValue
    private String respMsg;

    CodeEnum(String respCode, String respMsg) {
        this.respCode = respCode;
        this.respMsg = respMsg;
    }

    @Override
    public String getValue() {
        return this.respCode;
    }

    public String getMsg() {
        return this.respMsg;
    }
}
