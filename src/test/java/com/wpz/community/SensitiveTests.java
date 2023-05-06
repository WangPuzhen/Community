package com.wpz.community;

import com.wpz.community.dao.DiscussPostMapper;
import com.wpz.community.dao.LoginTicketMapper;
import com.wpz.community.entity.DiscussPost;
import com.wpz.community.entity.LoginTicket;
import com.wpz.community.util.SensitiveFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class) // 用run时的环境测试程序

public class SensitiveTests {

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Test
    public void testSensitiveFilter(){
        String text1 = "这里可以赌博，可以嫖娼，吸毒，开票---";
        text1 = sensitiveFilter.filter(text1);
        System.out.println(text1);

        String text2 = "这里可以&赌@博)，可以🤣嫖娼，吸❤😍毒，开票---";
        text2 = sensitiveFilter.filter(text2);
        System.out.println(text2);
    }
}
