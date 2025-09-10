package com.synergisticit.service;

import java.util.List;

import com.synergisticit.domain.User;

public interface UserService {

	User findByUsername(String username);
	User saveUser(User user);
	User findUserById(Long userId);
	List<User> findAll();
	User updateUserById(Long userId);
	void deleteUserById(Long userId);

}
