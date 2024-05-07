package com.itheima.dao;

import java.util.HashMap;

public interface BaseUserDao {
    //根据用户名查找该用户是否存在
    boolean isExitUser(String name);

    //根据用户名和密码匹配
    boolean isUser(String name, String pws);

    //添加一个新用户
    void addUser(String name, String pws);

    HashMap<String, String> getHashMap();


}
