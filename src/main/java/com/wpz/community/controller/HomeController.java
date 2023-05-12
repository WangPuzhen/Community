package com.wpz.community.controller;

import com.wpz.community.entity.DiscussPost;
import com.wpz.community.entity.Page;
import com.wpz.community.entity.User;
import com.wpz.community.service.DiscussPostService;
import com.wpz.community.service.LikeService;
import com.wpz.community.service.UserService;
import com.wpz.community.util.CommunityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController implements CommunityConstant {

    @Autowired
    private UserService userService;

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private LikeService likeService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String getIndexPage(Model model, Page page){ // 由Page来控制分页的相关信息
        // 方法调用之前，SpringMVC会自动实例化Model和Page，并将Page注入Model
        // 所以在thymeleaf中就可以直接访问Page对象中的数据,而不需要再model.addAttribute("page", page)
        page.setRows(discussPostService.getDisicussPostRows(0));
        page.setPath("/index");

        List<DiscussPost> list = discussPostService.findDiscussPosts(0,page.getOffset(), page.getLimit());
        List<Map<String, Object>> discussPosts = new ArrayList<>();
        if(list != null){
            for (DiscussPost discussPost : list) {
                Map<String, Object> map = new HashMap<>();
                map.put("post", discussPost);
                User user = userService.findUserById(discussPost.getUserId()); // 找到userId对应的User
                map.put("user", user);

                // 查询赞的数量
                long likeCount = likeService.findEntityLikeCount(ENTITY_TYPE_POST, discussPost.getId());
                map.put("likeCount", likeCount);

                discussPosts.add(map);
            }
        }

        model.addAttribute("discussPosts", discussPosts);

        return "index";
    }

    @RequestMapping(path = "/error", method = RequestMethod.GET)
    public String getErrorPage(){
        return "/error/500";
    }

}
