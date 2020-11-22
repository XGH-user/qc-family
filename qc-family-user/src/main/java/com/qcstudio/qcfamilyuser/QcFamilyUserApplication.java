package com.qcstudio.qcfamilyuser;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xgh
 * springboot启动类
 */
@SpringBootApplication
@MapperScan("com.qcstudio.qcfamilyuser.mapper")
public class QcFamilyUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(QcFamilyUserApplication.class, args);
    }

}
