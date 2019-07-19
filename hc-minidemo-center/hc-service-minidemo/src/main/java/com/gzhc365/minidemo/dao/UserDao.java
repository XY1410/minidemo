package com.gzhc365.minidemo.dao;

import com.gzhc365.minicommon.util.JdbcUtil;
import com.gzhc365.minidemo.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao implements MiniDao{
    private static final RowMapper<User> userRM = BeanPropertyRowMapper.newInstance(User.class);
    protected final Logger log = LogManager.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User getById(Long id){
        return JdbcUtil.queryForObject(jdbcTemplate, Sql.GETBYID, userRM, id);
    }

    public User getByName(String name){
        return jdbcTemplate.queryForObject(Sql.GETBYNAME , userRM,'%'+name+'%');
    }

    private static final class Sql {
        private static final String BASE = "id, name, createTime, updateTime ";
        private static final String GETBYID = "select " + BASE + " from t_md_user where id=? ";
        private static final String GETBYNAME = "select " + BASE + " from t_md_user where name like ? ";

    }

}
