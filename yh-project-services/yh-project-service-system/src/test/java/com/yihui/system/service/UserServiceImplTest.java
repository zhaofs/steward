package com.yihui.system.service;

import com.yihui.system.SystemManageApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Administrator on 2015/10/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SystemManageApplication.class)
public class UserServiceImplTest{

    @Autowired
    private  UserService userService;

    @Test
    public void testSay(){
        userService.say();
    }
}