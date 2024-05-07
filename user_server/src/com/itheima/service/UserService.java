package com.itheima.service;

import com.itheima.dao.BaseUserDao;
import com.itheima.factory.FactoryBaseUserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.Set;

public class UserService implements Runnable {
    public static final Logger LOGGER = LoggerFactory.getLogger("com.itheima.service.UserService");
    private final Socket socket;
    private final BaseUserDao userDao = FactoryBaseUserDao.getBaseUserDao();

    public UserService(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            String s = br.readLine();
            LOGGER.info("服务器收到信息" + s);
            String[] split = s.split("&");
            String s1 = split[0];
            String s2 = split[1];
            switch (s1) {
                case "1":
                    boolean login = login(s2);
                    bw.write(login + "");
                    bw.newLine();
                    bw.flush();
                    break;
                case "2":
                    bw.write(add(s2) + "");
                    bw.newLine();
                    bw.flush();
                    break;
                case "3":
                    Set<String> set = userDao.getHashMap().keySet();
                    for (String string : set) {
                        bw.write(string);
                        bw.newLine();
                        bw.flush();
                    }
                    bw.close();
                    break;
            }

        } catch (IOException e) {
            LOGGER.error("UserService服务器端出现异常：" + e.getMessage());
        }
    }

    private boolean add(String s) {
        String[] split = s.split(",");
        if (userDao.isExitUser(split[0])) {
            return false;
        }
        userDao.addUser(split[0], split[1]);
        return true;
    }

    private boolean login(String split) {
        String[] split1 = split.split(",");
        return userDao.isUser(split1[0], split1[1]);
    }
}
