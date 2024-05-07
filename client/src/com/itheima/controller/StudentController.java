package com.itheima.controller;

import com.itheima.utils.AgeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;

public class StudentController {
    private final static Logger LOGGER = LoggerFactory.getLogger("com.itheima.controller.StudentController");
    private final static HashSet<String> hashset = new HashSet<>();

    static {
        //初始化用于存放学生学号的集合
        getSet();
    }

    private Scanner scanner = new Scanner(System.in);

    private static void selectAllStudent(BufferedWriter writer, BufferedReader reader) throws IOException {
        writer.write("1&");
        writer.newLine();
        writer.flush();
        String line = reader.readLine();
        if (Objects.equals(line, null)) {
            System.out.println("还没有学生信息，请先添加！！！");
            System.out.println("------------------------------------------");
        } else {
            System.out.println("姓名\t\t\t学号\t\t\t\t性别\t\t生日\t\t\t\t年龄\t\t分数");
            while (line != null) {
                System.out.println(line);
                line = reader.readLine();
            }
            System.out.println("-------------------------------------------");
        }
    }

    private static Socket getSocket() {
        try {
            return new Socket("127.0.0.1", 2020);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private static void getSet() {
        try (Socket socket = getSocket();
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        ) {
            writer.write("2&");
            writer.newLine();
            writer.flush();
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                hashset.add(s);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void start() throws Exception {
        System.out.println("===================================欢迎进入学生管理系统=================================");
        while (true) {
            System.out.println("1,查看全部学生信息    2,添加学生    3,删除学生    4,修改学生     5,退出系统");
            int n;
            while (true) {
                try {
                    System.out.println("请选择：");
                    n = scanner.nextInt();
                    break;
                } catch (Exception e) {
                    LOGGER.error("输入不合法！！！");
                    scanner = new Scanner(System.in);
                }
            }
            Socket socket = getSocket();
            if (socket != null) {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                switch (n) {
                    case 1:
                        selectAllStudent(writer, reader);
                        break;
                    case 2:
                        addStudent(writer);
                        break;
                    case 3:
                        deleteStudent(writer);
                        break;
                    case 4:
                        setStudent(writer);
                        break;
                    case 5:
                        System.out.println("欢迎访问系统，期待下次光临！！！");
                        System.exit(0);
                        break;
                }
            }
        }
    }

    private void deleteStudent(BufferedWriter writer) throws IOException {
        while (true) {
            System.out.println("请输入要删除学生的学号：");
            String id = scanner.next();
            if (hashset.contains(id)) {
                writer.write("3&" + id);
                writer.flush();
                writer.close();
                System.out.println("学号为" + id + "的学生删除成功！！！");
                hashset.remove(id);
                break;
            } else {
                System.out.println("该学生不存在！！！");
            }
        }
    }

    private void setStudent(BufferedWriter writer) throws IOException {
        while (true) {
            System.out.println("请输入你要修改的学生学号：");
            String id = scanner.next();
            if (hashset.contains(id)) {
                writeStudent(writer, "4&", id, "恭喜你修改完成！！！");
                break;
            } else {
                System.out.println("要修改的学生不存在！！！");
            }
        }
    }

    private void writeStudent(BufferedWriter writer, String x, String id, String x1) throws IOException {
        System.out.println("请输入学生的姓名：");
        String name = scanner.next();
        System.out.println("请输入学生的性别：");
        String sex = scanner.next();
        String birthday;
        while (true) {
            System.out.println("请输入学生的生日：");
            birthday = scanner.next();
            try {
                AgeUtils.getAge(birthday);
                break;
            } catch (Exception e) {
                LOGGER.error("日期输入不合法，请重新输入！！！(yyyy-MM-dd)");
                scanner = new Scanner(System.in);
            }
        }
        System.out.println("请输入学生的分数：");
        String score = scanner.next();
        writer.write(x + name + "," + id + "," + sex + "," + birthday + "," + score);
        writer.newLine();
        writer.flush();
        System.out.println(x1);
    }

    private void addStudent(BufferedWriter writer) throws IOException {
        System.out.println("请输入要添加学生的学号：");
        String id = scanner.next();
        if (!hashset.contains(id)) {
            writeStudent(writer, "5&", id, "学生添加成功！！！");
            hashset.add(id);
        } else {
            System.out.println("学生重复！！！");
            addStudent(writer);
        }
    }
}
