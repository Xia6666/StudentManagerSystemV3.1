package com.itheima.factory;

import com.itheima.dao.BaseStudentDao;
import com.itheima.dao.StudentDao;

public class StudentDaoFactory {
    public static BaseStudentDao getBaseStudentDao() {
        return new StudentDao();
    }
}
