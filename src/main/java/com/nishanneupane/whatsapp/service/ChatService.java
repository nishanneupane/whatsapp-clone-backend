package com.nishanneupane.whatsapp.service;

import java.util.List;

import com.nishanneupane.whatsapp.exception.ChatException;
import com.nishanneupane.whatsapp.exception.UserException;
import com.nishanneupane.whatsapp.modal.Chat;
import com.nishanneupane.whatsapp.modal.User;
import com.nishanneupane.whatsapp.request.GroupchatRequest;

public interface ChatService {
	
	public Chat createChat(User reqUser,Integer userId2) throws UserException;
	public Chat findByChatId(Integer chatId) throws ChatException;
	public List<Chat> findAllChatByUserId(Integer userId)throws UserException;
	public Chat createGroup(GroupchatRequest req,User reqUser) throws UserException;
	public Chat addUserToGroup(Integer userId,Integer chatId,User reqUser) throws UserException,ChatException;
	public Chat renameGroup(Integer chatId,String groupName,User reqUser) throws UserException,ChatException;
	public Chat removeFromGroup(Integer chatId,Integer userId,User reqUser) throws UserException,ChatException;
	public void deleteChat(Integer chatId,Integer userId)throws ChatException,UserException;

}
