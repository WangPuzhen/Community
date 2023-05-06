package com.wpz.community.service;

import com.wpz.community.dao.AlphaDao;
import com.wpz.community.dao.DiscussPostMapper;
import com.wpz.community.dao.UserMapper;
import com.wpz.community.entity.DiscussPost;
import com.wpz.community.entity.User;
import com.wpz.community.util.CommunityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Date;

/**
 * 管理bean的一个测试类
 */
@Service
public class AlphaService {

    @Autowired
    private AlphaDao alphaDao; // 在service中调用dao

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private TransactionTemplate transactionTemplate;

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


    // 使用注解实现事务管理 （3.13 事务管理 38：00）

    // REQUIRED: 支持当前事务（外部事物），如果外部事务不存在，就创建新事务
    // REQUIRED_NEW： 创建一个新事务，并且暂停当前事务（外部事务）
    // NESTED：如果当前存在事务（外部事务）， 则嵌套在该事务中执行（独立的提交和回滚），否则就会REQUIRED一样
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED) // 事务的传播方式指的是当一个事务调用另一个事务时，应该按哪种标准执行事务
    public Object save1(){
        // 新增用户
        User user = new User();
        user.setUsername("alpha");
        user.setSalt(CommunityUtil.generaterUUID().substring(0,5));
        user.setPassword(CommunityUtil.md5("123" + user.getSalt()));
        user.setEmail("alpha@qq.com");
        user.setHeaderUrl("http://image.nowcoder.com/head/99t.png");
        user.setCreateTime(new Date());
        userMapper.insertUser(user);
        // 新增帖子
        DiscussPost post = new DiscussPost();
        post.setUserId(user.getId());
        post.setTitle("Hello!");
        post.setContent("新人报到！");
        post.setCreateTime(new Date());
        discussPostMapper.insertDiscussPost(post);

        Integer.valueOf("abc");
        return "ok";
    }

    // 使用编程实现事务
    public Object save2(){
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        return transactionTemplate.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus transactionStatus) {
                // 新增用户
                User user = new User();
                user.setUsername("beta");
                user.setSalt(CommunityUtil.generaterUUID().substring(0,5));
                user.setPassword(CommunityUtil.md5("123" + user.getSalt()));
                user.setEmail("beta@qq.com");
                user.setHeaderUrl("http://image.nowcoder.com/head/999t.png");
                user.setCreateTime(new Date());
                userMapper.insertUser(user);
                // 新增帖子
                DiscussPost post = new DiscussPost();
                post.setUserId(user.getId());
                post.setTitle("你好!");
                post.setContent("我是新人！");
                post.setCreateTime(new Date());
                discussPostMapper.insertDiscussPost(post);

                Integer.valueOf("abc");
                return "ok";
            }
        });
    }
}
