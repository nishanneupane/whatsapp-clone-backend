package com.nishanneupane.whatsapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nishanneupane.whatsapp.exception.ChatException;
import com.nishanneupane.whatsapp.exception.MessageException;
import com.nishanneupane.whatsapp.exception.UserException;
import com.nishanneupane.whatsapp.modal.Message;
import com.nishanneupane.whatsapp.modal.User;
import com.nishanneupane.whatsapp.request.SendMessageRequest;
import com.nishanneupane.whatsapp.response.ApiResponse;
import com.nishanneupane.whatsapp.service.ChatService;
import com.nishanneupane.whatsapp.service.MessageService;
import com.nishanneupane.whatsapp.service.UserService;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/create")
	public ResponseEntity<Message> sendMessageHandler(@RequestBody SendMessageRequest req,@RequestHeader("Authorization") String jwt) throws UserException, ChatException{
		Message message = messageService.sendMessage(req);
		User user=userService.findUserProfile(jwt);
		
		req.setUserId(user.getId());
		
		return new ResponseEntity<Message>(message,HttpStatus.CREATED);
	}
	
	@GetMapping("/chat/{chatId}")
	public ResponseEntity<List<Message>> findMessageHandler(@PathVariable Integer chatId, @RequestHeader("Authorization") String jwt) throws UserException, ChatException{

		User user=userService.findUserProfile(jwt);
		
		List<Message> messages = messageService.getChatsMessage(chatId, user);

		
		return new ResponseEntity<List<Message>>(messages,HttpStatus.CREATED);
	}
	
	
	@DeleteMapping("/{messageId}")
	public ResponseEntity<ApiResponse> deleteMessageHandler(@PathVariable Integer messageId, @RequestHeader("Authorization") String jwt) throws UserException, ChatException, MessageException{

		User user=userService.findUserProfile(jwt);
		
		messageService.deleteMessage(messageId, user);

		
		ApiResponse response=new ApiResponse("message deleted Sucessfully ",true);
		return new ResponseEntity<ApiResponse>(response,HttpStatus.CREATED);
	}

}
