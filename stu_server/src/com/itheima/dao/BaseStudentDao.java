package com.itheima.dao;

import com.itheima.pojo.Student;

import java.util.ArrayList;

public interface BaseStudentDao {
    //查找学生
    ArrayList<Student> selectAllStudent();

    //添加学生/修改学生
    void addStudent(String name, String id, String sex, String birthday, Integer age, Double score);

    //根据学号删除学生
    void deleteStudent(String id);

    //根据学号判断学生是否存在
    boolean isExist(String id);

    //修改学生
    void setStudent(String name, String id, String sex, String birthday, Integer age, Double score);
}
