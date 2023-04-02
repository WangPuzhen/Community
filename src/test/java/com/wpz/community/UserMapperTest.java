package com.wpz.community;

import com.wpz.community.dao.UserMapper;
import com.wpz.community.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class) // 用run时的环境测试程序
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelect(){
        User user = userMapper.selectById(101);
        System.out.println(user);

        user = userMapper.selectByName("liubei");
        System.out.println(user);

        user = userMapper.selectByEmail("nowcoder101@sina.com");
        System.out.println(user);
    }

    @Test
    public void testInsert(){
        User user = new User();
        user.setUsername("wpz");
        user.setPassword("123456");
        user.setEmail("45432@qq.com");
        user.setSalt("xxx");
        user.setType(1);
        user.setStatus(1);
        user.setActivationCode("jihuoma");
        user.setHeaderUrl("www.wpz.com");
        user.setCreateTime(new Date());

        int rows = userMapper.insertUser(user);
        System.out.println(rows);

    }

    @Test
    public void testUpdate(){
        int rows = userMapper.updateHeader(150, "www.updatewpz.com");
        userMapper.updatePassword(150, "654321");
        userMapper.updateStatus(150, 0);
    }
}
