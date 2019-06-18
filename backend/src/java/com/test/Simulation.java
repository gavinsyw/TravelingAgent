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


public class Simulation extends HttpServlet {
　　
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException  {
        try{
            Class.forName("org.sqlite.JDBC");
            String db = "C:\\\\Users\\\\SJTUwwz\\\\SEDB.db";
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + db);
            Statement state = conn.createStatement();
            System.out.println("这是simulation请求");
            response.setContentType("text/html;charset=utf-8");
            String name;
            double popularity;
            int hotid;
            int sight_id;
            double price;
            double total;
            double environment;
            double service;
            double latitude;
            double longitude;
            String description;
            Vector<Spot> result = new Vector<Spot>(90);
            String city_id = request.getParameter("city_id");
            ResultSet rs_hotel = state.executeQuery("select * from hotel where city_id =\'"+ city_id + "\';");
            System.out.println("city: "+city_id);
            while(rs_hotel.next()){
                name = rs_hotel.getString("name");
                popularity = rs_hotel.getDouble("popularity");
                hotid = rs_hotel.getInt("hotel_id");
                price = rs_hotel.getDouble("price");
                total = rs_hotel.getDouble("total");
                latitude = rs_hotel.getDouble("latitude");
                longitude = rs_hotel.getDouble("longitude");
                description = rs_hotel.getString("description");
                Hotel hot = new Hotel(name, hotid, 1, popularity, price, total, longitude, latitude, description);
                result.add(hot);
            }
            ResultSet rs_sight = state.executeQuery("select * from sight where city_id =\'"+ city_id + "\';");
            while(rs_sight.next()){
                name = rs_sight.getString("name");
                popularity = rs_sight.getDouble("popularity");
                sight_id = rs_sight.getInt("sight_id");
                price = rs_sight.getDouble("price");
                total = rs_sight.getDouble("total");
                environment = rs_sight.getDouble("environment");
                service = rs_sight.getDouble("service");
                latitude = rs_sight.getDouble("latitude");
                longitude = rs_sight.getDouble("longitude");
                description = rs_sight.getString("description");
                Sight sight = new Sight(name, sight_id, 0, description, longitude, latitude, popularity, total, environment, service, price);
                result.add(sight);
            }
            //fun()
//            arg: input 6 choice 1 city_id
//                 output spot list
            Gson gson = new Gson();
            String jsonObject = gson.toJson(result);
            System.out.println(jsonObject);
            response.getWriter().write(jsonObject);
            state.close();
            conn.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }


}