package com.itheima.dao;

import com.itheima.pojo.Student;

import java.io.*;
import java.util.ArrayList;

public class StudentDao implements BaseStudentDao {
    private final static ArrayList<Student> students = new ArrayList<>();

    static {
        try (BufferedReader br = new BufferedReader(new FileReader("stu_server/students.txt"))) {
            String s = br.readLine();
            while (s != null) {
                String[] split = s.split(",");
                students.add(new Student(split[0], split[1], split[2], split[3], Integer.valueOf(split[4]), Double.valueOf(split[5])));
                s = br.readLine();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void writeStudent() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("stu_server/students.txt"));
            for (Student student : students) {
                bw.write(student.toString());
                bw.newLine();
                bw.flush();
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ArrayList<Student> selectAllStudent() {
        return students;
    }

    @Override
    public void addStudent(String name, String id, String sex, String birthday, Integer age, Double score) {
        students.add(new Student(name, id, sex, birthday, age, score));
        writeStudent();
    }

    public void setStudent(String name, String id, String sex, String birthday, Integer age, Double score) {
        deleteStudent(id);
        students.add(new Student(name, id, sex, birthday, age, score));
        writeStudent();
    }

    @Override
    public void deleteStudent(String id) {
        for (Student student : students) {
            if (student.getId().equals(id)) {
                students.remove(student);
                break;
            }
        }
        writeStudent();
    }

    @Override
    public boolean isExist(String id) {
        for (Student student : students) {
            if (student.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

}
