package com.wpz.community.dao;

import com.wpz.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussPostMapper {

    // 首页显示其实不需要userId，但考虑到以后开发用户的个人主页，其中有个我发布的帖子，会用到userId来搜索，所以加上
    // 加上后就要考虑userId啥时候用，啥时候不用，所以用到动态sql
    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit); // 参数考虑分页功能

    // @Param注解用于给参数取别名
    // 如果只有一个参数，且在<if>中使用，则必须加别名
    int selectDiscussPostRows(@Param("userId") int userId); // 查询表中共有多少条数据，方便展示页码

    int insertDiscussPost(DiscussPost discussPost);

    DiscussPost selectDiscussPostById(int id);
}
