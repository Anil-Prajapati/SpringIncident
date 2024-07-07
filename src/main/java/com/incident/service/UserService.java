package com.incident.service;

import java.security.Principal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.incident.model.Role;
import com.incident.model.User;
import com.incident.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserRepository userRepository;

	public Iterable<User> getAll() {
		return userRepository.findAll();
	}

	public User getSingleData(String userName) {
		return userRepository.findByUserName(userName);
	}

	public User create(User user) {
		// Encrypt the user's password
		String password = user.getPassword();
		String encrypt = bCryptPasswordEncoder.encode(password);
		user.setPassword(encrypt);
		user.setDate(new Date());

		// Set roles for the user
		Set<Role> roles = new HashSet<>();
		Role role = new Role();
		role.setRoleName("User");
		role.setDescription("This Is The User Role");
		roles.add(role);
		user.setRoles(roles);

		return userRepository.save(user);
	}

	public User forgetPassword(String email, String password) {
		User user = userRepository.findByEmailIgnoreCase(email);

		if (user != null) {
			String encrypt = bCryptPasswordEncoder.encode(password);
			user.setPassword(encrypt);
			return userRepository.save(user);
		}

		return null;
	}

	// get current user

	public User getCurrentUser(Principal principal) {
		String username = principal.getName();
		return userRepository.findByUserName(username);
	}
}
