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
import com.google.gson.reflect.TypeToken;
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


public class SendItinerary extends HttpServlet {
　　
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException  {
        try{
            Class.forName("org.sqlite.JDBC");
            String db = "C:\\\\Users\\\\SJTUwwz\\\\SEDB.db";
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + db);
            Statement state = conn.createStatement();
            System.out.println("这是send itinerary请求");
            response.setContentType("text/html;charset=utf-8");
            int user_id;
            int itineraryID;
            int city_id;
            String itinerary;
            user_id = Integer.parseInt(request.getParameter("user_id"));
            System.out.println(user_id);
            List<Spot> path;
            Gson gson = new Gson();
            System.out.println(user_id);
            Vector<Itinerary> result = new Vector<Itinerary>(1000);
            ResultSet rs_itinerary = state.executeQuery("select * from user_history where user_id =\'"+ user_id + "\';");
            while(rs_itinerary.next()){
                user_id = rs_itinerary.getInt("user_id");
                itineraryID = rs_itinerary.getInt("itineraryID");
                city_id = rs_itinerary.getInt("city_id");
                itinerary = rs_itinerary.getString("itinerary");
                path = gson.fromJson(itinerary, new TypeToken<List<Spot>>(){}.getType());
                Itinerary it = new Itinerary(itineraryID, city_id, user_id, path);
                result.add(it);
//                System.out.println("a path!");
            }
            String jsonObject = gson.toJson(result);
            response.getWriter().write(jsonObject);
            System.out.println(jsonObject);
            state.close();
            conn.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }
//    public void doPost(HttpServletRequest request, HttpServletResponse response)
//        throws ServletException, IOException {
//        System.out.println("这是post请求");
//        StringBuffer jb = new StringBuffer();
//        String line = null;
//        Gson gson = new Gson();
//        try {
//            Class.forName("org.sqlite.JDBC");
//            String db = "C:\\\\Users\\\\SJTUwwz\\\\SEDB.db";
//            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + db);
//            Statement state = conn.createStatement();
//            response.setContentType("text/html;charset=utf-8");
//            BufferedReader reader = request.getReader();
//            while ((line = reader.readLine()) != null)
//                jb.append(line);
//            response.getWriter().write(jb.toString());
//            System.out.println(jb.toString());
//            Itinerary itinerary = gson.fromJson(jb.toString(), Itinerary.class);
//            int user_id = itinerary.getUserID();
//            int itinerary_id = itinerary.getItineraryID();
//            String jsonObject = gson.toJson(itinerary.getSpotList());
//            state.execute("insert into user_his (user_id, itinerary) values (\'" + user_id + "\',\'" + jsonObject +"\');");
//            state.close();
//            conn.close();
//        } catch (Exception e) {
//        /*report an error*/ 
//            System.out.println(e);
//        }

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