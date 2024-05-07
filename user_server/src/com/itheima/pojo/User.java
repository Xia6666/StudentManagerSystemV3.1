package com.itheima.pojo;

import java.util.Objects;

public class User {
    private String useName;
    private String password;

    public User() {
    }

    public User(String useName, String password) {
        this.useName = useName;
        this.password = password;
    }

    public String getUseName() {
        return useName;
    }

    public void setUseName(String useName) {
        this.useName = useName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "useName='" + useName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        User user = (User) object;
        return Objects.equals(useName, user.useName) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(useName, password);
    }
}
