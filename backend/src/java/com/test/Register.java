/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test;

/**
 *
 * @author SJTUwwz
 */

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Register extends HttpServlet {
　　
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException  {
        try{
            Class.forName("org.sqlite.JDBC");
            String db = "C:\\\\Users\\\\SJTUwwz\\\\SEDB.db";
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + db);
            Statement state = conn.createStatement();
            System.out.println("这是register请求");
            response.setContentType("text/html;charset=utf-8");
            String userName = request.getParameter("username");
            String userPass = request.getParameter("userpass");
            String mail = request.getParameter("mail");
            System.out.println(userName + " " + userPass+" "+mail);
            if (userName != null && userPass != null && mail != null) {
                ResultSet rs = state.executeQuery("select * from users where username =\""+ userName +"\" or mail =\""+mail+"\";");
                if (!rs.next()) {
                    System.out.println("666!");
                    String sql = "insert into users (username, userpwd, mail) values (\""+userName+"\", \""+userPass+"\", \""+mail+"\");";
                    state.execute(sql);
                    String sql2 = "select ID from users where username=\'" + userName + "\';";
                    ResultSet rs2 = state.executeQuery(sql2);
                    String id = rs2.getString("ID");
//                    response.getWriter().write("Successfully register!");
                    response.getWriter().write(id);
                } else {
//                    response.getWriter().write("用户名或者邮箱已存在！");
                      response.getWriter().write("0");
                }
            } else {
//                response.getWriter().write("用户名或者密码或者邮箱为空！");
                response.getWriter().write("0");
            }
            state.close();
            conn.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }


}
