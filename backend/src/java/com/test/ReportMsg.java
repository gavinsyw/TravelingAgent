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


public class ReportMsg extends HttpServlet {
　　
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException  {
        try{
            Class.forName("org.sqlite.JDBC");
            String db = "C:\\\\Users\\\\SJTUwwz\\\\SEDB.db";
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + db);
            Statement state = conn.createStatement();
            System.out.println("这是reportmsg请求");
            response.setContentType("text/html;charset=utf-8");
            String msg = request.getParameter("msg");
            System.out.println(msg);
            if (msg != null) {
                ResultSet rs0 = state.executeQuery("select * from reportMsg where msg_content =\""+ msg +"\";");
                if (!rs0.next()) {
                    System.out.println("666!");
                    String sql = "insert into reportMsg (msg_content) values (\""+msg+"\");";
                    state.execute(sql);
                    response.getWriter().write("感谢你的反馈！");
                } else {
                    response.getWriter().write("相同的报告内容已存在！");
                }
            } else {
                response.getWriter().write("报告内容为空！");
            }
            state.close();
            conn.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }


}

