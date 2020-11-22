package com.qcstudio.qcfamilyuser.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.qcstudio.qcfamilyuser.enums.CodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author xgh
 * 用于封装后端返回前端数据对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Slf4j
@Component
public class ResultInfo implements Serializable {

    /**
     * 后端session返回结果token
     */
    private String token;

    private String msg;

    /**
     * 操作码枚举
     */
    @JSONField(serialzeFeatures= SerializerFeature.WriteEnumUsingToString)
    private CodeEnum codeEnum;
    /**
     * 后端返回结果正常为true，发生异常返回false
     */
    private boolean flag;
    /**
     * 后端返回结果数据对象
     */
    private Object data;

    public ResultInfo(String token) {
        this.token = token;
    }

    public ResultInfo(boolean flag , CodeEnum codeEnum) {
        this.codeEnum = codeEnum;
        this.flag = flag;
    }

    public ResultInfo(boolean flag , CodeEnum codeEnum , Object data) {
        this.codeEnum = codeEnum;
        this.flag = flag;
        this.data = data;
    }

    public ResultInfo(boolean flag , CodeEnum codeEnum , String msg) {
        this.codeEnum = codeEnum;
        this.flag = flag;
        this.msg = msg;
    }

    private ResultInfo(boolean flag , CodeEnum codeEnum , String msg, Object data){
        this.codeEnum = codeEnum;
        this.flag = flag;
        this.msg = msg;
        this.data = data;
    }
}
