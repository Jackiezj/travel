package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResultInfo info = new ResultInfo();
        UserService userService = new UserServiceImpl();

        // 将登录信息封装为user对象
        Map<String, String[]> parameterMap = request.getParameterMap();
        User beanUser = new User();
        try {
            BeanUtils.populate(beanUser, parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        // 调用service方法登录查询用户
        User user = userService.login(beanUser);

        // 用户名或密码错误
        if (user == null) {
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误");
        }
        // 用户存在但没有激活时处理
        if (user != null && !"Y".equalsIgnoreCase(user.getStatus())) {
            info.setFlag(false);
            info.setErrorMsg("用户没有激活");
        }
        // 登录成功处理
        if (user != null && "Y".equalsIgnoreCase(user.getStatus())) {
            request.getSession().setAttribute("user", user);
            info.setFlag(true);
            info.setErrorMsg("登录成功");
        }

        // 返回响应
        response.setContentType("application/json;charset=utf-8");
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), info);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
