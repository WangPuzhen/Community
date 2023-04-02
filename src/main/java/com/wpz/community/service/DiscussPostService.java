package com.wpz.community.service;

import com.wpz.community.dao.DiscussPostMapper;
import com.wpz.community.entity.DiscussPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscussPostService {
    @Autowired
    private DiscussPostMapper discussPostMapper;

    // 这里查询到的DiscussPost有一个外键userId,但是显示的时候肯定是显示用户名而不是userId，所以需要再通过userId查询到User，再把User和discusspost拼在同一个数据结构中去，所以需要额外再写一个UserService
    public List<DiscussPost> findDiscussPosts(int userId, int offset, int limit){
        return discussPostMapper.selectDiscussPosts(userId, offset, limit);
    }

    public int getDisicussPostRows(int userId){
        return discussPostMapper.selectDiscussPostRows(userId);
    }
}
