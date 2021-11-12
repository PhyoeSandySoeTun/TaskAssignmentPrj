package com.mab.task.dao;

import java.util.List;

import com.mab.task.model.User;

public interface UserDao {
	List<User> getResourceUserList() throws Exception;
	List<User> getResListByName(String name) throws Exception;
	List<User> searchUser(String name) throws Exception;
}
