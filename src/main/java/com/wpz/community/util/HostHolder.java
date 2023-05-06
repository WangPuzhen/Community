package com.wpz.community.util;

import com.wpz.community.entity.User;
import org.springframework.stereotype.Component;

/**
 * 考虑多线程情况，持有用户信息，代替session结构
 */

@Component
public class HostHolder {

    private ThreadLocal<User> users = new ThreadLocal<>();

    public void setUsers(User user){
        users.set(user);
    }

    public User getUser(){
        return users.get();
    }

    public void clear(){
        users.remove();
    }
}
