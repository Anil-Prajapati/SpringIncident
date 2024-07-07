package com.incident.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.incident.model.JwtRequest;
import com.incident.model.JwtResponse;
import com.incident.model.User;
import com.incident.repository.UserRepository;
import com.incident.util.JwtUtil;

@Service
public class JwtService implements UserDetailsService {

	@Autowired
	@Lazy
	private UserRepository userRepository;

	@Autowired
	@Lazy
	private AuthenticationManager authenticationManager;

	@Autowired
	@Lazy
	private JwtUtil jwtUtil;

	public JwtResponse createJwtToken(JwtRequest jwtRequest) {
		String userName = jwtRequest.getUserName();
		String password = jwtRequest.getPassword();
		
		if (userName == null || userName.isEmpty()) {
			throw new UsernameNotFoundException("User Name is not found...");
		}

		User user = findByUserNameOrEmailOrContactNumber(userName, 0);

		if (user == null || !authentication(user.getUserName(), password)) {
			throw new UsernameNotFoundException("Bad Credencial userName and password");
		}

		final UserDetails userDetails = loadUserByUsername(userName);
		String generateToken = jwtUtil.generateToken(userDetails);

		return new JwtResponse(user, generateToken);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = userRepository.findById(username).orElse(null);
		if (user != null) {
			return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
					getAuthorities(user));
		} else {
			throw new UsernameNotFoundException("User Name is Requied...");
		}
	}

	public Set<SimpleGrantedAuthority> getAuthorities(User user) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<SimpleGrantedAuthority>();
		user.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
		});
		return authorities;
	}

	private boolean authentication(String userName, String password) {

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
			return true;
		} catch (DisabledException e) {
			// TODO: handle exception
			System.out.println("user Name is Disable...");
			return false;
		} catch (BadCredentialsException e) {
			// TODO: handle exception
			System.out.println("Bad Credential from the user name...");
			return false;
		}

	}

	private User findByUserNameOrEmailOrContactNumber(String userNameOrEmailOrContactNumber, long contactNumber) {

		User user = userRepository.findById(userNameOrEmailOrContactNumber).orElse(null);

		if (user == null) {
			user = userRepository.findByEmailIgnoreCase(userNameOrEmailOrContactNumber);
		}

		if (user == null) {
			user = userRepository.findByContactNumber(contactNumber);
		}

		return user;
	}

}
