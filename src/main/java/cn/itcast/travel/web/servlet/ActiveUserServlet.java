package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/activeUserServlet")
public class ActiveUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取激活码
        String code = request.getParameter("code");
        UserService userService = new UserServiceImpl();
        User realUser = userService.findByCode(code);
        String msg = "";
        if (realUser == null) {
            msg = "<h1>激活失败, 请联系管理员<h1>";
        }

        // 激活用户
        int count = userService.activeUser(realUser);
        if (count != 0) {
            msg = "激活成功, 请直接<h1><a href='http://localhost/travel/login.html'>登录</a><h1>";
        } else {
            msg = "<h1>激活失败, 请联系管理员<h1>";
        }
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(msg);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
