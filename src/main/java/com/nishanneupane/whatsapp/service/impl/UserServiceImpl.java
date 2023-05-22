package com.nishanneupane.whatsapp.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nishanneupane.whatsapp.config.TokenProvider;
import com.nishanneupane.whatsapp.exception.UserException;
import com.nishanneupane.whatsapp.modal.User;
import com.nishanneupane.whatsapp.repository.UserRepository;
import com.nishanneupane.whatsapp.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TokenProvider tokenProvider;
	

	@Override
	public User findUserById(Integer id) {
		Optional<User> opt = userRepository.findById(id);
		
		if(opt.isPresent()) {
			return opt.get();
		}
		throw new UserException("User not found with user id "+id);
	}

	@Override
	public User findUserProfile(String jwt) {
		String email = tokenProvider.getEmailFromToken(jwt);
		
		if(email==null) {
			throw new BadCredentialsException("received invalid token .........");
		}
		User user=userRepository.findByEmail(email);
		
		if(user==null) {
			throw new UsernameNotFoundException("User not found with email id "+email);
		}
		return user;
	}

	@Override
	public User updateUser(Integer userId, com.nishanneupane.whatsapp.request.UpdateUserRequest req) throws UserException {
		User user=findUserById(userId);
		
		if(req.getFull_name()!=null) {
			user.setFull_name(req.getFull_name());
		}
		if(req.getProfile_picture()!=null) {
			user.setProfile_picture(req.getProfile_picture());
		}
		
		return userRepository.save(user);
		
	}

	@Override
	public List<User> searchUser(String query) {
		List<User> users=userRepository.searchUser(query);
		
		return users;
	}

}
