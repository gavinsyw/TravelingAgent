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
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;


public class SaveItinerary extends HttpServlet {
　　
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException  {
        try{
            Class.forName("org.sqlite.JDBC");
            String db = "C:\\\\Users\\\\SJTUwwz\\\\SEDB.db";
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + db);
            Statement state = conn.createStatement();
            System.out.println("这是save itinerary请求");
            response.setContentType("text/html;charset=utf-8");
            String json;
            int user_id;
            user_id = Integer.parseInt(request.getParameter("user_id"));
            json = request.getParameter("json");
            state.execute("insert into user_history (user_id, itinerary) values (\'" + user_id + "\',\'" + json +"\');");
            response.getWriter().write(json);
            state.close();
            conn.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        System.out.println("这是save itinerary请求");
        StringBuffer jb = new StringBuffer();
        String line = null;
        Gson gson = new Gson();
        try {
            Class.forName("org.sqlite.JDBC");
            String db = "C:\\\\Users\\\\SJTUwwz\\\\SEDB.db";
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + db);
            Statement state = conn.createStatement();
            response.setContentType("text/html;charset=utf-8");
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
            response.getWriter().write(jb.toString());
            System.out.println(jb.toString());
            Itinerary itinerary = gson.fromJson(jb.toString(), Itinerary.class);
            int user_id = itinerary.getUserID();
            int city_id = itinerary.getCityID();
            int itinerary_id = itinerary.getItineraryID();
            String jsonObject = gson.toJson(itinerary.getSpotList());
            state.execute("insert into user_history (user_id, city_id, itinerary) values (\'" + user_id + "\',\'" + city_id +"\',\'"+ jsonObject+"\');");
            System.out.println("user_id "+user_id);
            state.close();
            conn.close();
        } catch (Exception e) {
        /*report an error*/ 
            System.out.println(e);
        }

//        try {
//            Gson jsonObject =  HTTP.toJSONObject(jb.toString());
//        } catch (Exception e) {
//            // crash and burn
//            throw new IOException("Error parsing JSON request string");
//        }

  // Work with the data using methods like...
  // int someInt = jsonObject.getInt("intParamName");
  // String someString = jsonObject.getString("stringParamName");
  // JSONObject nestedObj = jsonObject.getJSONObject("nestedObjName");
  // JSONArray arr = jsonObject.getJSONArray("arrayParamName");
  // etc...
    }

}