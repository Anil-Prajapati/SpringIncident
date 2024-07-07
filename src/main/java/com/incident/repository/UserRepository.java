package com.incident.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incident.model.User;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, String> {

	public User findByUserNameOrEmailOrContactNumber(String userName, String email, long contactNumber);
	public User findByEmailIgnoreCase(String email);
	public User findByContactNumber(long contactNumber);
	
	public User findByUserName(String userName);
	
}
