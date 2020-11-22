package com.qcstudio.qcfamilyuser.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.qcstudio.qcfamilyuser.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;

/**
 * @author xgh
 * 角色实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Slf4j
public class Role extends Model<Role> implements Serializable {

    @TableId("id")
    private Integer id;
    private String name;
    private String remark;

    @JSONField(serialzeFeatures= SerializerFeature.WriteEnumUsingToString)
    private RoleEnum type;

    @TableField(exist = false)
    private List<User> users;

    @TableField(exist = false)
    private List<Permission> permissions;
}

