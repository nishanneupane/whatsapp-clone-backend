package com.nishanneupane.whatsapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nishanneupane.whatsapp.modal.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	public User findByEmail(String email);
	
	@Query("select u from User u where u.full_name Like %:query% or u.email Like %:query%")
	public List<User> searchUser(String query);
}
