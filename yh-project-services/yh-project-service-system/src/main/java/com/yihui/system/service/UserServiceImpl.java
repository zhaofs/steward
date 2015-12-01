package com.yihui.system.service;

import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService{
	public void say(){
		System.out.println("hello word");
	}
}
