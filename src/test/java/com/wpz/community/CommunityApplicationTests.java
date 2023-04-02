package com.wpz.community;


import com.wpz.community.config.AlphaConfig;
import com.wpz.community.dao.AlphaDao;
import com.wpz.community.service.AlphaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class) // 用run时的环境测试程序
public class CommunityApplicationTests implements ApplicationContextAware {
    // 哪一个类想获得Spring容器，就实现这个接口
    private ApplicationContext applicationContext; // allicationContext就是Spring容器

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext; // applicationContext会被Spring自动扫描传入参数
    }

    @Test
    public void testApplicationContext(){ // 测试容器
        System.out.println(applicationContext);

        AlphaDao alphaDao = applicationContext.getBean(AlphaDao.class);
        System.out.println(alphaDao.select());

        alphaDao = applicationContext.getBean("Hibernate", AlphaDao.class); // 通过注解给定的名称获取Bean
        System.out.println(alphaDao.select());
    }

    @Test
    public void testBeanManage(){
        AlphaService alphaService = applicationContext.getBean(AlphaService.class);
        System.out.println(alphaService);
    }

    @Test
    public void testBeanConfig(){
        SimpleDateFormat simpleDateFormat = applicationContext.getBean(SimpleDateFormat.class);
        System.out.println(simpleDateFormat.format(new Date()));
    }

    // 这行之上，都是我们主动在获取Bean，但是更方便的是使用依赖注入的方式

    @Autowired // 这句话的作用就是希望spring容器将AlphaDao这个Bean注入给alphaDao
    @Qualifier("Hibernate") // 按名称注入
    private AlphaDao alphaDao;

    @Autowired
    private AlphaService alphaService;

    @Autowired
    private SimpleDateFormat simpleDateFormat;

    @Test
    public void testDependeceInjection(){
        System.out.println(alphaDao.select());
        System.out.println(alphaService);
        System.out.println(simpleDateFormat.format(new Date()));
    }


}
