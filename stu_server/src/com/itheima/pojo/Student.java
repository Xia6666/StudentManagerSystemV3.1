package com.itheima.pojo;

import java.util.Objects;

public class Student {
    private String name;
    private String id;
    private String sex;
    private String birthday;
    private Integer age;
    private Double score;

    public Student() {
    }

    public Student(String name, String id, String sex, String birthday, Integer age, Double score) {
        this.name = name;
        this.id = id;
        this.sex = sex;
        this.birthday = birthday;
        this.age = age;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Student student = (Student) object;
        return Objects.equals(name, student.name) && Objects.equals(id, student.id) && Objects.equals(sex, student.sex) && Objects.equals(birthday, student.birthday) && Objects.equals(age, student.age) && Objects.equals(score, student.score);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, sex, birthday, age, score);
    }

    @Override
    public String toString() {
        return name + "," + id + "," + sex + "," + birthday + "," + age + "," + score;
    }

    public String toString1() {
        if (name.length() == 2)
            return name + "\t\t\t" + id + "\t\t" + sex + "\t\t" + birthday + "\t\t" + age + "\t\t" + score;
        else {
            return name + "\t\t" + id + "\t\t" + sex + "\t\t" + birthday + "\t\t" + age + "\t\t" + score;
        }
    }
}
