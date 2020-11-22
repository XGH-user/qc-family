package com.qcstudio.qcfamilyuser;

import com.qcstudio.qcfamilyuser.entity.Role;
import com.qcstudio.qcfamilyuser.entity.User;
import com.qcstudio.qcfamilyuser.enums.RoleEnum;
import com.qcstudio.qcfamilyuser.enums.SexEnum;
import com.qcstudio.qcfamilyuser.mapper.RoleMapper;
import com.qcstudio.qcfamilyuser.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TestRoleMapper {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Test
    public void testFindAll(){
        List<Role> roles = roleMapper.selectList(null);
        roles.forEach(role -> System.out.println("role: " + role));
    }

    @Test
    public void testSelectRolesByPermissionId(){
        List<Role> roles = roleMapper.selectRolesByPermissionId(1);
        roles.forEach(role -> System.out.println("role :" + role));
    }

    @Test
    public void testEnum(){
        User user = new User();
        user.setUsername("测试1").setPassword("123").setNumber("123").setSex(SexEnum.FEMALE).setRole(RoleEnum.CAPABLE)
                .setSalt("123").setDepartment("理事会").setJwtsalt("123");
        int insert = this.userMapper.insert(user);
        System.out.println("result: " + insert);
    }

    @Test
    public void testFindUserAll(){
        List<User> users = this.userMapper.selectList(null);
        users.forEach(user -> System.out.println(users));
    }
}
