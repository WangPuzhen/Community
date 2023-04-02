package com.wpz.community.service;

import com.wpz.community.dao.AlphaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 管理bean的一个测试类
 */
@Service
public class AlphaService {

    @Autowired
    private AlphaDao alphaDao; // 在service中调用dao

    public AlphaService(){
        System.out.println("实例化Bean");
    }

    @PostConstruct // 在构造方法之后调用
    public void init(){
        System.out.println("初始化Bean");
    }

    @PreDestroy // 在销毁之前调用
    public void destroy(){
        System.out.println("销毁Bean");
    }

    // 从dao中拿数据
    public String find(){
        return alphaDao.select();
    }
}
