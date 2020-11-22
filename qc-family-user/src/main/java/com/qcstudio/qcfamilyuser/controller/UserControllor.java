package com.qcstudio.qcfamilyuser.controller;

import com.qcstudio.qcfamilyuser.entity.ResultInfo;
import com.qcstudio.qcfamilyuser.entity.User;
import com.qcstudio.qcfamilyuser.enums.CodeEnum;
import com.qcstudio.qcfamilyuser.exceptions.IllegalParameterException;
import com.qcstudio.qcfamilyuser.exceptions.UserExistException;
import com.qcstudio.qcfamilyuser.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xgh
 */
@RestController
@RequestMapping("/user")
public class UserControllor {

    @Autowired
    private UserService userService;

    @Autowired
    private ResultInfo info;

    @PostMapping("/register")
    public ResultInfo register(User user){
        try{
            userService.register(user);
            info.setCodeEnum(CodeEnum.SUCCESS);
            info.setFlag(true);
        }catch (UserExistException e){
            info.setFlag(false);
            info.setCodeEnum(CodeEnum.ERROR);
            info.setMsg("用户已存在，注册失败");
        }catch (IllegalParameterException e){
            info.setFlag(false);
            info.setCodeEnum(CodeEnum.ERROR);
            info.setMsg("非法参数，注册失败");
        }catch (Exception e){
            info.setFlag(false);
            info.setCodeEnum(CodeEnum.ERROR);
            info.setMsg("注册失败");
        }

        return info;
    }

    /**
     * 退出登录
     * 销毁主体的认证记录（信息），下次访问需要重新认证
     *
     * @return
     */
    @RequestMapping("/logout")
    public ResultInfo logout(){
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        userService.logout(user);

        subject.logout();
        info.setFlag(true);
        info.setCodeEnum(CodeEnum.SUCCESS);
        return info;
    }

    /**
     * 用户登录（身份认证）
     * Shiro会缓存认证信息
     *
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/login")
    public ResultInfo login(String username, String password) {
        // 前期的注入工作已经由SpringBoot完成了
        // 获取当前来访用户的主体对象
        Subject subject = SecurityUtils.getSubject();

        try {
            // 执行登录，如果登录失败会直接抛出异常，并进入对应的catch
            subject.login(new UsernamePasswordToken(username, password));

            // 获取主体的身份信息
            // 实际上是User。为什么？
            // 取决于LoginRealm中的doGetAuthenticationInfo()方法中SimpleAuthenticationInfo构造函数的第一个参数
            User user = (User) subject.getPrincipal();

            // 生成jwt
            String jwt = userService.generateJwt(user);

            // 将jwt放入到响应头中
//            ResponseEntity.ok().header("token", jwt).build();

            info.setFlag(true);
            info.setCodeEnum(CodeEnum.SUCCESS);
            info.setToken(jwt);
            return info;

        } catch (UnknownAccountException e) {
            // username 错误
            e.printStackTrace();
            info.setFlag(false);
            info.setCodeEnum(CodeEnum.INVALID_USER);
            return info;
        } catch (IncorrectCredentialsException e) {
            // password 错误
            e.printStackTrace();
            info.setFlag(false);
            info.setCodeEnum(CodeEnum.INVALID_PASSWORD);
            return info;
        }
    }
}
