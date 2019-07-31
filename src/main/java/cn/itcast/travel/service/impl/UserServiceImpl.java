package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {
    private UserDao dao = new UserDaoImpl();

    @Override
    public boolean register(User user) {
        User u = dao.findByUsername(user);
        if (u != null) {
            return false;
        }
        // 设置激活状态
        user.setCode(UuidUtil.getUuid());
        user.setStatus("N");

        dao.save(user);

        // 发送邮件
        String content = "<a href='http://localhost/travel/activeUserServlet?code="+user.getCode()+"'>点击激活</a>";

        MailUtils.sendMail(user.getEmail(), content, "激活邮件");

        return true;
    }

    @Override
    public User findByCode(String code) {
        return dao.findByCode(code);
    }

    @Override
    public int activeUser(User realUser) {
        return dao.activeUser(realUser);
    }

    @Override
    public User login(User beanUser) {
        return dao.findbyUsernameAndPassword(beanUser.getUsername(), beanUser.getPassword());
    }
}
