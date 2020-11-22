package com.qcstudio.qcfamilyuser.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.qcstudio.qcfamilyuser.enums.RoleEnum;
import com.qcstudio.qcfamilyuser.enums.SexEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.io.Serializable;
import java.util.List;

/**
 * @author xgh
 * 用户实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Slf4j
@EntityScan
public class User extends Model<User> implements Serializable {

    @TableId("id")
    private Integer id;
    private String username;
    private String password;

    @JSONField(serialzeFeatures= SerializerFeature.WriteEnumUsingToString)
    private SexEnum sex;

    private String number;
    private String department;

    private RoleEnum role;

    private String salt;

    private String jwtsalt;

    @TableField(select = false)
    @Version
    private Integer version;

    @TableField(select = false)
    @TableLogic
    private Integer deleted;

    @TableField(select = false,exist = false)
    private List<Role> roles;
}
