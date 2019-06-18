package com.example.travelingagent.entity;

public class User {
    private int ID;
    private String name;
    private String password;
    private String mail;
    private String phoneNumber;

    User(int ID, String name, String password, String mail, String phoneNumber) {
        this.ID = ID;

        this.name = name;
        this.password = password;
        this.mail = mail;
        this.phoneNumber = phoneNumber;

    }

    public int getID() {
        return this.ID;
    }

    public boolean setName(String newName) {
        this.name = newName;
        return true;
    }

    public String getName() {
        return this.name;
    }

    public boolean setPassword(String newPassword) {
        if (!checkPasswordValid(newPassword)) {
            return false;
        }

        this.password = newPassword;
        return true;
    }

    public String  getPassword() {
        return this.password;
    }

    public boolean setMail(String newMail) {
        if (!checkMailValid(newMail)) {
            return false;
        }

        this.mail = newMail;
        return true;
    }

    public String  getMail() {
        return this.mail;
    }

    public boolean setPhoneNumber(String newPhoneNumber) {
        if (!checkPhoneNumberValid(newPhoneNumber)) {
            return false;
        }

        this.phoneNumber = newPhoneNumber;
        return true;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
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
