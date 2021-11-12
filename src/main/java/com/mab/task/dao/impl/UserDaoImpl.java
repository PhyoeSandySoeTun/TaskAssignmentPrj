package com.mab.task.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mab.task.dao.UserDao;
import com.mab.task.model.User;

@Repository
public class UserDaoImpl implements UserDao {
	
	@Autowired
	@Qualifier("uat_jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	@Qualifier("uat_namedJdbcTemplate")
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	

	@Override
	public List<User> getResourceUserList() throws Exception {
		List<User> resUser = new ArrayList<User>();
		String sql = "SELECT * FROM task_user ORDER BY userId";
		
		List<User> list = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(User.class));
		if (list.size() != 0) {
			resUser = list;
		}
		return resUser;
	}

	@Override
	public List<User> getResListByName(String name) throws Exception {
		List<User> resUser = new ArrayList<User>();
		String sql = "SELECT * FROM task_user WHERE name = '"+name+"'ORDER BY userId";
		
		List<User> list = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(User.class));
		if (list.size() != 0) {
			resUser = list;
		}
		return resUser;
	}

	@Override
	public List<User> searchUser(String name) throws Exception {
		List<User> resUser = new ArrayList<User>();
		String sql = "SELECT * FROM task_user WHERE name LIKE :name ORDER BY name";
		String finalName= "%" + name + "%";
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("name", finalName);
		
		List<User> list = namedJdbcTemplate.query(sql, paramSource, BeanPropertyRowMapper.newInstance(User.class));
		if (list.size() != 0) {
			resUser = list;
		}
		return resUser;
	}

}
