package com.example.travelingagent.myclass;

public class User {
    private String usrName;
    private String usrPwd;
    boolean VIP;

    public User(String usrName, String usrPwd) {
        this.usrName = usrName;
        this.usrPwd = usrPwd;
        this.VIP = false;
    }
}