package com.nishanneupane.whatsapp.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nishanneupane.whatsapp.exception.ChatException;
import com.nishanneupane.whatsapp.exception.MessageException;
import com.nishanneupane.whatsapp.exception.UserException;
import com.nishanneupane.whatsapp.modal.Chat;
import com.nishanneupane.whatsapp.modal.Message;
import com.nishanneupane.whatsapp.modal.User;
import com.nishanneupane.whatsapp.repository.MessageRepository;
import com.nishanneupane.whatsapp.request.SendMessageRequest;
import com.nishanneupane.whatsapp.service.ChatService;
import com.nishanneupane.whatsapp.service.MessageService;
import com.nishanneupane.whatsapp.service.UserService;


@Service
public class MessageServiceImpl implements MessageService{
	
	@Autowired
	private MessageRepository messageRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ChatService chatService;

	@Override
	public Message sendMessage(SendMessageRequest req) throws UserException, ChatException {
		User user=userService.findUserById(req.getUserId());
		Chat chat=chatService.findByChatId(req.getChatId());
		
		Message message=new Message();
		message.setChat(chat);
		message.setUser(user);
		message.setContent(req.getContent());
		message.setTimestamp(LocalDateTime.now());;
		return message;
	}

	@Override
	public List<Message> getChatsMessage(Integer chatId,User reqUser) throws ChatException {
		
		Chat chat=chatService.findByChatId(chatId);
		if(!chat.getUsers().contains(reqUser)) {
			throw new UserException(" you are not related to this chat "+chat.getId());
		}
		
		List<Message> messages = messageRepository.findByChatId(chat.getId());
		
		return messages;
	}

	@Override
	public Message findMessageById(Integer messageId) throws MessageException {
		Optional<Message> opt = messageRepository.findById(messageId);
		
		if(opt.isPresent()) {
			return opt.get();
		}
		throw new MessageException("message not found with id "+messageId);
	}

	@Override
	public void deleteMessage(Integer messageId,User reqUser) throws MessageException {
		Message message=findMessageById(messageId);
		
		if(message.getUser().getId().equals(reqUser.getId())) {
			messageRepository.deleteById(messageId);
		}
		
		throw new UserException("you cant delete another users messsage "+reqUser.getFull_name());
		
	}

}
