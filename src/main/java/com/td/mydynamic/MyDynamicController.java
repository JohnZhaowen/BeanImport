package com.td.mydynamic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author: zhaowen.he
 * date: 2020/1/22
 * ticket:
 * description:
 */
@RestController
public class MyDynamicController {

    @Autowired
    private UserMapper userMapper;


    @GetMapping("/user")
    public String getUser(Long id, String tenant){
        return userMapper.getUser(id, tenant);
    }
}
