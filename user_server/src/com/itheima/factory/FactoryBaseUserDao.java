package com.itheima.factory;

import com.itheima.dao.BaseUserDao;
import com.itheima.dao.UserDao;

public class FactoryBaseUserDao {
    public static BaseUserDao getBaseUserDao() {
        return new UserDao();
    }
}
