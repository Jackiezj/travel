package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {
    User findByUsername(User user);

    void save(User user);

    User findByCode(String code);

    int activeUser(User realUser);
}
