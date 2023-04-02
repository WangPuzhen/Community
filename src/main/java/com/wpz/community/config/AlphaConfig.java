package com.wpz.community.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;

@Configuration // 表示这是一个配置类，通常用来装配Java自带的方法，或者他人的方法
public class AlphaConfig {

    @Bean // 方法名就是Bean的名字
    public SimpleDateFormat simpleDataFormat(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
}
