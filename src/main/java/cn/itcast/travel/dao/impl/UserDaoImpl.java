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
        String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email) values(?,?,?,?,?,?,?)";
        try {
            template.update(sql, user.getUsername(),
                    user.getPassword(),
                    user.getName(),
                    user.getBirthday(),
                    user.getSex(),
                    user.getTelephone(),
                    user.getEmail());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
}
