package com.spring.hms.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.hms.entity.Admin;
import com.spring.hms.entity.User;
import com.spring.hms.repository.UserRepository;


@Service("userService")
public class UserService {

	private UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	public User findByConfirmationToken(String confirmationToken) {
		return userRepository.findByConfirmationToken(confirmationToken);
	}
	
	public void saveUser(User user) {
		userRepository.save(user);
	}
	
	public List<User> findAll() {
		
		return userRepository.findAll();
	}

	public void removeDoctor(Integer id) {
		userRepository.deleteById(id);
		
	}

	public void removeUser(Integer id) {
		userRepository.deleteById(id);
		
	}

	public User findUserById(Integer id) {
		return userRepository.findById(id).get();
		
	}

}