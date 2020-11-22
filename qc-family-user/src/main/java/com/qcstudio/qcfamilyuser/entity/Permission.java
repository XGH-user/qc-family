package com.qcstudio.qcfamilyuser.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;

/**
 * @author xgh
 * 权限实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Slf4j
public class Permission extends Model<Permission> implements Serializable {

    @TableId("id")
    private Integer id;
    @TableField(value = "t_name")
    private String name;
    @TableField(value = "t_permission")
    private String permission;

    @TableField(exist = false)
    private List<Role> roles;
}
