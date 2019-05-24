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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Login extends HttpServlet {
　　
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("这是get请求");
        response.setContentType("text/html;charset=utf-8");
        String userName = request.getParameter("username");
        String userPass = request.getParameter("userpass");
        System.out.println(userName + " " + userPass);
        if (userName != null || userPass != null) {
            if (userName.equals("chp") && userPass.equals("123456")) {
                response.getWriter().write("恭喜" + userName + "登陆成功！");
            } else {
                response.getWriter().write("用户名或者密码错误！");
            }
        } else {
            response.getWriter().write("用户名或者密码为空！");
        }

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("这是post请求");
        response.setContentType("text/html;charset=utf-8");
        String userName = request.getParameter("username");
        String userPass = request.getParameter("userpass");
        System.out.println(userName + " " + userPass);
        if (userName != null || userPass != null) {
            if (userName.equals("chp") && userPass.equals("123456")) {
                response.getWriter().write("恭喜" + userName + "登陆成功！");
            } else {
                response.getWriter().write("用户名或者密码错误！");
            }
        } else {
            response.getWriter().write("用户名或者密码为空！");
        }

    }

}
