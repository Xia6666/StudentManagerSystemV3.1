package com.itheima;

import com.itheima.controller.StudentController;
import com.itheima.controller.UserController;

public class ClientApplication {
    public static void main(String[] args) throws Exception {
        new UserController().start();
        new StudentController().start();
    }
}
