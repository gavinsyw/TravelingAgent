package com.example.lbstest.myclass;

import java.util.SortedSet;
import java.util.TreeSet;

public class User {
    private int ID;
    private String name;
    private String password;
    private String mail;
    private String phoneNumber;

    private static int totalNumber = 0;
    private static SortedSet<String> names = new TreeSet<String>();

    public User(int ID, String name, String password, String mail, String phoneNumber) {
        this.ID = ID;
        this.name = name;
        this.password = password;
        this.mail = mail;
        this.phoneNumber = phoneNumber;

        totalNumber++;
        names.add(name);
    }

    public boolean setName(String newName) {
        if (names.contains(newName)) {
            return false;
        }

        this.name = newName;
        return true;
    }

    public boolean setPassword(String newPassword) {
        if (!checkPasswordValid(newPassword)) {
            return false;
        }

        this.password = newPassword;
        return true;
    }

    public boolean setMail(String newMail) {
        if (!checkMailValid(newMail)) {
            return false;
        }

        this.mail = newMail;
        return true;
    }

    public boolean setPhoneNumber(String newPhoneNumber) {
        if (!checkPhoneNumberValid(newPhoneNumber)) {
            return false;
        }

        this.phoneNumber = newPhoneNumber;
        return true;
    }

    /**
     * 覆写此方法以设置用户密码标准
     */
    private boolean checkPasswordValid(String password) {
        return true;
    }

    /**
     * 覆写此方法以设置邮箱标准
     */
    private boolean checkMailValid(String mail) {
        return true;
    }

    /**
     * 覆写此方法以设置邮箱标准
     */
    private boolean checkPhoneNumberValid(String phoneNumber) {
        return true;
    }
}
