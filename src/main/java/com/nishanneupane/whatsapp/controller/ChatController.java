package com.nishanneupane.whatsapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nishanneupane.whatsapp.exception.ChatException;
import com.nishanneupane.whatsapp.exception.UserException;
import com.nishanneupane.whatsapp.modal.Chat;
import com.nishanneupane.whatsapp.modal.User;
import com.nishanneupane.whatsapp.request.GroupchatRequest;
import com.nishanneupane.whatsapp.request.SingleChatRequest;
import com.nishanneupane.whatsapp.response.ApiResponse;
import com.nishanneupane.whatsapp.service.ChatService;
import com.nishanneupane.whatsapp.service.UserService;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

	@Autowired
	private ChatService chatService;

	@Autowired
	private UserService userService;

	@PostMapping("/single")
	public ResponseEntity<Chat> createChatHandler(@RequestBody SingleChatRequest singleChatRequest,
			@RequestHeader("Authorization") String jwt) {

		User reqUser = userService.findUserProfile(jwt);
		Chat chat = chatService.createChat(reqUser, singleChatRequest.getUserId());

		return new ResponseEntity<Chat>(chat, HttpStatus.OK);
	}

	@PostMapping("/group")
	public ResponseEntity<Chat> createGroupHandler(@RequestBody GroupchatRequest req,
			@RequestHeader("Authorization") String jwt) {

		User reqUser = userService.findUserProfile(jwt);
		Chat chat = chatService.createGroup(req, reqUser);

		return new ResponseEntity<Chat>(chat, HttpStatus.OK);
	}

	@GetMapping("/{chatId}")
	public ResponseEntity<Chat> findChatByIdHandler(@PathVariable Integer chatId,
			@RequestHeader("Authorization") String jwt) throws ChatException {

		Chat chat = chatService.findByChatId(chatId);

		return new ResponseEntity<Chat>(chat, HttpStatus.OK);
	}

	@GetMapping("/user")
	public ResponseEntity<List<Chat>> findAllChatByUserHandler(@RequestHeader("Authorization") String jwt) {

		User reqUser = userService.findUserProfile(jwt);
		List<Chat> chats = chatService.findAllChatByUserId(reqUser.getId());

		return new ResponseEntity<List<Chat>>(chats, HttpStatus.OK);
	}

	@PutMapping("/{chatId}/add/{userId}")
	public ResponseEntity<Chat> addUserToGroupHandler(@PathVariable Integer chatId,@PathVariable Integer userId, @RequestHeader("Authorization") String jwt) throws UserException, ChatException {

		User reqUser = userService.findUserProfile(jwt);
		Chat chat = chatService.addUserToGroup(userId, chatId, reqUser);

		return new ResponseEntity<Chat>(chat, HttpStatus.OK);
	}
	
	@PutMapping("/{chatId}/remove/{userId}")
	public ResponseEntity<Chat> removeUserFromGroupHandler(@PathVariable Integer chatId,@PathVariable Integer userId, @RequestHeader("Authorization") String jwt) throws UserException, ChatException {

		User reqUser = userService.findUserProfile(jwt);
		Chat chat = chatService.removeFromGroup(chatId, userId, reqUser);

		return new ResponseEntity<Chat>(chat, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{chatId}")
	public ResponseEntity<ApiResponse> deleteChatHandler(@PathVariable Integer chatId, @RequestHeader("Authorization") String jwt) throws UserException, ChatException {

		User reqUser = userService.findUserProfile(jwt);
		chatService.deleteChat(chatId, reqUser.getId());

		
		ApiResponse response=new ApiResponse("Chat deleted Sucessfully ",true);
				
		return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
	}

}
