package com.wpz.community;

import com.wpz.community.util.MailClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class) // 用run时的环境测试程序
public class MailTest {

    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void testMail(){
        mailClient.sendMail("450491011@qq.com", "Test", "Hello community.");
    }

    @Test
    public void testMailHtml(){
        Context context = new Context();
        context.setVariable("username", "WangPuzhen");

        String content = templateEngine.process("/mail/demo", context);

        mailClient.sendMail("450491011@qq.com", "Html Demo", content);
    }
}
