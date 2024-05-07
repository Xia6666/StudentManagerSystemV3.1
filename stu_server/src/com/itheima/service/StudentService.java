package com.itheima.service;

import com.itheima.dao.BaseStudentDao;
import com.itheima.factory.StudentDaoFactory;
import com.itheima.pojo.Student;
import com.itheima.utils.AgeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;


public class StudentService implements Runnable {
    private final static Logger LOGGER = LoggerFactory.getLogger("com.itheima.service.StudentService");
    private final static BaseStudentDao studentDao = StudentDaoFactory.getBaseStudentDao();

    private final Socket socket;

    public StudentService(Socket socket) {
        this.socket = socket;
    }

    private static ArrayList<Student> selectAllStudent() {
        return studentDao.selectAllStudent();
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
            String s1 = br.readLine();
            String[] split = s1.split("&");
            LOGGER.info("服务器端收到客户端的报文:" + split[0]);
            switch (split[0]) {
                case "1":
                    ArrayList<Student> students = selectAllStudent();
                    for (Student student : students) {
                        bw.write(student.toString1());
                        bw.newLine();
                        bw.flush();
                    }
                    break;
                case "2":
                    ArrayList<Student> students1 = selectAllStudent();
                    for (Student student : students1) {
                        bw.write(student.getId());
                        bw.newLine();
                        bw.flush();
                    }
                    break;
                case "3":
                    studentDao.deleteStudent(split[1]);
                    break;
                case "4":
                    String[] split2 = split[1].split(",");
                    LOGGER.info("客户端传来的修改数据:" + split[1]);
                    setStudent(split2[0], split2[1], split2[2], split2[3], Double.valueOf(split2[4]));
                    break;
                case "5":
                    String[] split1 = split[1].split(",");
                    addStudent(split1[0], split1[1], split1[2], split1[3], Double.valueOf(split1[4]));
                    break;
            }

        } catch (Exception e) {

            LOGGER.error("StudentService方法中出现异常:" + e.getMessage());
        }
    }

    private void addStudent(String name, String id, String sex, String birthday, Double score) {
        Integer age = AgeUtils.getAge(birthday);
        studentDao.addStudent(name, id, sex, birthday, age, score);
    }

    private void setStudent(String name, String id, String sex, String birthday, Double score) {
        Integer age = AgeUtils.getAge(birthday);
        studentDao.setStudent(name, id, sex, birthday, age, score);
    }

}
