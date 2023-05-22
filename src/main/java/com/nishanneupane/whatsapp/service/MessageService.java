package com.nishanneupane.whatsapp.service;

import java.util.List;

import com.nishanneupane.whatsapp.exception.ChatException;
import com.nishanneupane.whatsapp.exception.MessageException;
import com.nishanneupane.whatsapp.exception.UserException;
import com.nishanneupane.whatsapp.modal.Message;
import com.nishanneupane.whatsapp.modal.User;
import com.nishanneupane.whatsapp.request.SendMessageRequest;

public interface MessageService {
	
	public Message sendMessage(SendMessageRequest
			req) throws UserException,ChatException;
	
	public List<Message> getChatsMessage(Integer chatId,User reqUser) throws ChatException;
	
	public Message findMessageById(Integer messageId) throws MessageException;
	
	public void deleteMessage(Integer messageId,User reqUser) throws MessageException;

}
