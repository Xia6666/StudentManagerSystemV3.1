package com.itheima.controller;

import java.io.*;
import java.net.Socket;
import java.util.HashSet;
import java.util.Scanner;

public class UserController {
    private final static HashSet<String> hashSet = new HashSet<>();

    static {
        getUserName();
    }

    private Scanner scanner = new Scanner(System.in);

    private static Socket getSocket() {
        try {
            return new Socket("127.0.0.1", 2121);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    private static void getUserName() {
        try (Socket socket = getSocket();
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            bw.write("3&2");
            bw.newLine();
            bw.flush();
            String s;
            while ((s = br.readLine()) != null) {
                hashSet.add(s);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void start() {
        System.out.println("===========欢迎进入学生管理系统V3.0用户登陆界面===========");
        System.out.println("1,用户登陆");
        System.out.println("2,新用户注册");
        System.out.println("3,退出系统");
        while (true) {
            System.out.println("请选择: ");
            int n;
            while (true) {
                try {
                    n = scanner.nextInt();
                    break;
                } catch (Exception e) {
                    scanner = new Scanner(System.in);
                    System.err.println("输入不合法，请重新输入");
                }
            }
            switch (n) {
                case 1:
                    System.out.println("-------欢迎进入用户登陆界面------");
                    login();
                    return;
                case 2:
                    System.out.println("-------欢迎进入用户注册界面-------");
                    addUser();
                    break;
                case 3:
                    System.out.println("欢迎访问系统，期待下次再见！");
                    System.exit(0);
                    return;
                default:
                    System.out.println("选择有误，请重新输入：");
                    break;
            }
        }
    }

    private void addUser() {
        String name;
        int j = 3;
        while (true) {
            if (j == 0) {
                System.out.println("用户名重复次数过多，系统自动退出！！！");
                System.exit(0);
            }
            System.out.println("请输入要注册的用户名的：");
            name = scanner.next();
            if (hashSet.contains(name)) {
                j--;
                System.out.println("该用户已经存在！！！还剩" + j + "次尝试机会！！！");
            } else break;
        }
        System.out.println("请输入密码：");
        String pws = scanner.next();
        Socket socket = getSocket();
        try {
            if (socket != null) {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                writer.write("2&" + name + "," + pws);
                writer.newLine();
                writer.flush();
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String s = reader.readLine();
                if (s.equals("true")) {
                    System.out.println("恭喜你注册成功！！！");
                    hashSet.add(name);
                    return;
                }
                System.out.println("用户名被占用！！！");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void login() {
        String name;
        int j = 3;
        while (true) {
            if (j == 0) {
                System.out.println("错误次数太多！！！系统自动退出！！！");
                System.exit(0);
            }
            System.out.println("请输入用户名：");
            name = scanner.next();
            if (!hashSet.contains(name)) {
                j--;
                System.out.println("该用户不存在！！！还剩" + j + "次尝试机会！！！");
            } else break;
        }
        int k = 3;
        while (true) {
            if (k == 0) {
                System.out.println("错误次数太多！！！系统自动退出！！！");
                System.exit(0);
            }
            System.out.println("请输入密码：");
            String pws = scanner.next();
            Socket socket = getSocket();
            try {
                if (socket != null) {
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    bw.write("1&" + name + "," + pws);
                    bw.newLine();
                    bw.flush();
                    BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String s = br.readLine();
                    if ("true".equals(s)) {
                        System.out.println("恭喜你登陆成功！");
                        return;
                    } else {
                        k--;
                        System.out.println("密码不正确！！!还剩" + k + "次尝试机会！！！");
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}


