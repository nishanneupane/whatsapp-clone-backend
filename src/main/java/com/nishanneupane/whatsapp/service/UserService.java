package com.nishanneupane.whatsapp.service;

import java.util.List;

import com.nishanneupane.whatsapp.exception.UserException;
import com.nishanneupane.whatsapp.modal.User;
import com.nishanneupane.whatsapp.request.UpdateUserRequest;

public interface UserService {
	public User findUserById(Integer id);
	public User findUserProfile(String jwt);
	public User updateUser(Integer userId,UpdateUserRequest req) throws UserException;
	public List<User> searchUser(String query);
}
