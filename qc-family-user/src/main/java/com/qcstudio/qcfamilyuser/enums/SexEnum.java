package com.qcstudio.qcfamilyuser.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author xgh
 * 性别枚举类
 */

public enum SexEnum implements IEnum<Integer> {
    /**
     *
     */
    MALE(1,"男"),
    FEMALE(2,"女");

    public int value;
    @JsonValue
    public final String desc;

    SexEnum(int value,String desc){
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.desc;
    }
}
