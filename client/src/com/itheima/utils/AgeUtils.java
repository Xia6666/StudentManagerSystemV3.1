package com.itheima.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AgeUtils {
    private AgeUtils(){}
    private static Logger LOGGER= LoggerFactory.getLogger("com.itheima.utils.AgeUtils");
    public static Integer getAge(String s)
    {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = simpleDateFormat.parse(s);
            return (int)((System.currentTimeMillis()-date.getTime())/1000/60/60/24/365);
        } catch (ParseException e) {
            LOGGER.error("日期转化异常："+e.getMessage());
            return null;
        }
    }
}
