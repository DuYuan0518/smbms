package com.kuang.servlet.user;

import com.kuang.pojo.User;
import com.kuang.service.user.UserServiceImpl;
import com.kuang.util.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    public LoginServlet() {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("LoginServlet--start...");
        //获取用户名和密码
        String userCode = req.getParameter("userCode");
        String userPassword = req.getParameter("userPassword");
        //和数据库中的密码进行对比，调用业务层；
        UserServiceImpl userService = new UserServiceImpl();
        User user = userService.login(userCode,userPassword);
        if (user != null){//查有此人，可以登录
            //将用户的信息放入session中
            req.getSession().setAttribute(Constants.User_Session,user);
            //跳转到主页
            resp.sendRedirect("jsp/frame.jsp");
        }else{//查无此人，不可登录
            //转发会登陆页面，顺带提示他，用户名或密码错误
            req.setAttribute("error","用户名或密码错误");
            req.getRequestDispatcher("login.jsp").forward(req,resp);

        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
