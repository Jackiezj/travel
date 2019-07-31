package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

public interface UserService {
    boolean register(User user);

    User findByCode(String code);

    int activeUser(User realUser);

    User login(User beanUser);
}
