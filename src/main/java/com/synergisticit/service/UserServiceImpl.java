package com.synergisticit.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.synergisticit.domain.User;
import com.synergisticit.repository.UserRepository;

@Service   
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public User findUserById(Long userId) {
		return userRepository.findById(userId).orElse(null);
	}

	@Override
	public User updateUserById(Long userId) {
		return userRepository.findById(userId).orElse(null);
	}

	@Override
    public User saveUser(User user) {
        // Always encode before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public void deleteUserById(Long userId) {
		 userRepository.deleteById(userId);
	}
}
