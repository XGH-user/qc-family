package com.qcstudio.qcfamilyuser.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author xgh
 * 角色枚举类
 */
public enum RoleEnum implements IEnum<Integer> {
    /**
     *
     */
    PRESIDENT(1,"理事会"),
    MINISTER(2,"部长"),
    CAPABLE(3,"小干"),
    PREVIOUS(4,"往届师兄师姐");

    private int value;
    @JsonValue
    private final String desc;

    RoleEnum(int value,String desc){
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    @Override
    public String toString(){
        return this.desc;
    }
}
