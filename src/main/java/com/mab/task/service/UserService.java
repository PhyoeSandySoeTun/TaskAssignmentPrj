package com.mab.task.service;

import java.util.List;

import com.mab.task.model.User;

public interface UserService {
	List<User> getResourceUserList() throws Exception;
	List<User> getResListByName(String name) throws Exception;
	List<User> searchUser(String name) throws Exception;
}
