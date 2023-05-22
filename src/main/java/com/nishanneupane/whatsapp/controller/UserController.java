package com.nishanneupane.whatsapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nishanneupane.whatsapp.modal.User;
import com.nishanneupane.whatsapp.request.UpdateUserRequest;
import com.nishanneupane.whatsapp.response.ApiResponse;
import com.nishanneupane.whatsapp.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/profile")
	public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String token){
		User user = userService.findUserProfile(token);
		
		return new ResponseEntity<User>(user,HttpStatus.ACCEPTED);
		
	}
	
	@GetMapping("/{query}")
	public ResponseEntity<List<User>> searchUserHandler(@PathVariable String query){
		
		return new ResponseEntity<List<User>>(userService.searchUser(query),HttpStatus.OK);
		
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<ApiResponse> updateUserhandler(@RequestBody UpdateUserRequest updateUserRequest,@RequestHeader("Authorization")String token){
		
		User user=userService.findUserProfile(token);
		userService.updateUser(user.getId(), updateUserRequest);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("User updated sucessfully",true),HttpStatus.ACCEPTED);
	}

}
