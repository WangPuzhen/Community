package com.wpz.community.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class AlphaDaoMyBatis implements AlphaDao{
    @Override
    public String select() {
        return "MyBatis";
    }
}
