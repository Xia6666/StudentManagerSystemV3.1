package com.itheima.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

public class UserDao implements BaseUserDao {
    public static final Logger LOGGER = LoggerFactory.getLogger("com.itheima.dao.UserDao");
    private final static HashMap<String, String> hashMap = new HashMap<>();
    private final static Properties properties = new Properties();

    static {
        try {
            properties.load(new FileInputStream("user_server/user.properties"));
            Set<String> set = properties.stringPropertyNames();
            for (String s : set) {
                hashMap.put(s, properties.getProperty(s));
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public boolean isExitUser(String name) {
        return hashMap.containsKey(name);
    }

    @Override
    public boolean isUser(String name, String pws) {
        if (!hashMap.containsKey(name))
            return false;
        return pws.equals(hashMap.get(name));
    }

    @Override
    public void addUser(String name, String pws) {
        hashMap.put(name, pws);
        writeProperties();
    }

    @Override
    public HashMap<String, String> getHashMap() {
        return hashMap;
    }

    public void writeProperties() {
        hashMap.forEach(properties::setProperty);
        try {
            properties.store(new FileOutputStream("user_server/user.properties"), " ");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
