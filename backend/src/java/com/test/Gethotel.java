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
import com.google.gson.Gson;
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


public class Gethotel extends HttpServlet {
　　
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException  {
        try{
            Class.forName("org.sqlite.JDBC");
            String db = "C:\\\\Users\\\\SJTUwwz\\\\SEDB.db";
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + db);
            Statement state = conn.createStatement();
            System.out.println("这是gethotel请求");
            response.setContentType("text/html;charset=utf-8");
            String hotel_id = request.getParameter("hotel_id");
            String city_id = request.getParameter("city_id");
            System.out.println(city_id);
            if (hotel_id != null && city_id != null) {
                ResultSet rs = state.executeQuery("select * from hotel where hotel_id =\'"+ hotel_id + "\' and city_id =\'"+ city_id + "\';");
//                rs.next();
                if(rs.next()) {
//                    rs = state.executeQuery("select * from users where mail =\'"+ mail +"\';");
                    String name = rs.getString("name");
                    double popularity = rs.getDouble("popularity");
                    int hotid = rs.getInt("hotel_id");
                    double price = rs.getDouble("price");
                    double total = rs.getDouble("score");
                    double latitude = rs.getDouble("latitude");
                    double longitude = rs.getDouble("longitude");
                    Hotel hot = new Hotel(name, hotid, 0, popularity, price, total, latitude, longitude);
                    Gson gson = new Gson();
                    String jsonObject = gson.toJson(hot);
                    System.out.println("name:"+name);
                    System.out.println(jsonObject);
//                        response.getWriter().write("Welcome! "+ name);
                    response.getWriter().write(jsonObject);
                } else {
//                    response.getWriter().write("用户名或者密码错误！");
                      response.getWriter().write("0");
                }
            } else {
//                response.getWriter().write("用户名或者密码为空！");
                  response.getWriter().write("0");
            }
            state.close();
            conn.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }


}
