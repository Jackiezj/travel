package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public User findByUsername(User user) {
        String sql = "select * from tab_user where username = ?";
        User existUser = null;
        try {
            existUser = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), user.getUsername());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return existUser;
    }

    @Override
    public void save(User user) {
        String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code) values(?,?,?,?,?,?,?,?,?)";
        try {
            template.update(sql, user.getUsername(),
                    user.getPassword(),
                    user.getName(),
                    user.getBirthday(),
                    user.getSex(),
                    user.getTelephone(),
                    user.getEmail(),
                    user.getStatus(),
                    user.getCode()
            );
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User findByCode(String code) {
        String sql = "select * from tab_user where code = ?";
        User realUser = null;
        try {
            realUser = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return realUser;
    }

    @Override
    public int activeUser(User realUser) {
        int count = 0;
        String sql = "update tab_user set status = \"Y\" where code = ?";
        try {
            count = template.update(sql, realUser.getCode());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public User findbyUsernameAndPassword(String username, String password) {
        String sql = "select * from tab_user where username = ? and password = ?";
        User existUser = null;
        try {
            existUser = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, password);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return existUser;
    }
}
