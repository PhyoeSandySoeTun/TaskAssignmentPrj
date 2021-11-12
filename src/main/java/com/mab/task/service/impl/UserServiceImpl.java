package com.mab.task.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mab.task.dao.UserDao;
import com.mab.task.model.User;
import com.mab.task.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired 
	private UserDao userDao;

	@Override
	public List<User> getResourceUserList() throws Exception {
		return userDao.getResourceUserList();
	}

	@Override
	public List<User> getResListByName(String name) throws Exception {
		return userDao.getResListByName(name);
	}

	@Override
	public List<User> searchUser(String name) throws Exception {
		return userDao.searchUser(name);
	}

}
