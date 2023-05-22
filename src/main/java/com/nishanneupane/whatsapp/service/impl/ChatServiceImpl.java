package com.nishanneupane.whatsapp.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nishanneupane.whatsapp.exception.ChatException;
import com.nishanneupane.whatsapp.exception.UserException;
import com.nishanneupane.whatsapp.modal.Chat;
import com.nishanneupane.whatsapp.modal.User;
import com.nishanneupane.whatsapp.repository.ChatRepository;
import com.nishanneupane.whatsapp.request.GroupchatRequest;
import com.nishanneupane.whatsapp.service.ChatService;
import com.nishanneupane.whatsapp.service.UserService;

@Service
public class ChatServiceImpl implements ChatService {

	@Autowired
	private ChatRepository chatRepository;

	@Autowired
	private UserService userService;

	@Override
	public Chat createChat(User reqUser, Integer userId2) throws UserException {
		User user = userService.findUserById(userId2);

		Chat isChatExists = chatRepository.findSingleChatByUSerIds(user, reqUser);

		if (isChatExists != null) {
			return isChatExists;
		}
		Chat chat = new Chat();
		chat.setCreatedBy(reqUser);
		chat.getUsers().add(user);
		chat.getUsers().add(reqUser);
		chat.setGroup(false);

		return chat;
	}

	@Override
	public Chat findByChatId(Integer chatId) throws ChatException {
		Optional<Chat> chat = chatRepository.findById(chatId);

		if (chat.isPresent()) {
			return chat.get();
		}
		throw new ChatException("Chat not found with id " + chatId);
	}

	@Override
	public Chat createGroup(GroupchatRequest req, User reqUser) throws UserException {
		Chat group = new Chat();
		group.setGroup(true);
		group.setChat_image(req.getChat_image());
		group.setChat_name(req.getChat_name());
		group.setCreatedBy(reqUser);
		group.getAdmins().add(reqUser);

		for (Integer userId : req.getUserIds()) {
			User user = userService.findUserById(userId);
			group.getUsers().add(user);
		}

		return group;
	}

	@Override
	public Chat addUserToGroup(Integer userId, Integer chatId, User reqUser) throws UserException, ChatException {
		Optional<Chat> opt = chatRepository.findById(chatId);
		User user = userService.findUserById(userId);

		if (opt.isPresent()) {
			Chat chat = opt.get();

			if (chat.getAdmins().contains(reqUser)) {
				chat.getUsers().add(user);

				return chatRepository.save(chat);
			} else {
				throw new UserException("You are not admin ");
			}

		}

		throw new ChatException("Chat not found with chat id " + chatId);
	}

	@Override
	public Chat renameGroup(Integer chatId, String groupName, User reqUser) throws UserException, ChatException {
		Optional<Chat> opt = chatRepository.findById(chatId);

		if (opt.isPresent()) {
			Chat chat = opt.get();
			if (chat.getUsers().contains(reqUser)) {
				chat.setChat_name(groupName);
				return chatRepository.save(chat);
			}
			throw new UserException("you are not member of this group ..");
		}

		throw new ChatException("Chat not found with chat id " + chatId);
	}

	@Override
	public Chat removeFromGroup(Integer chatId, Integer userId, User reqUser) throws UserException, ChatException {
		Optional<Chat> opt = chatRepository.findById(chatId);
		User user = userService.findUserById(userId);

		if (opt.isPresent()) {
			Chat chat = opt.get();

			if (chat.getAdmins().contains(reqUser)) {
				chat.getUsers().remove(user);

				return chatRepository.save(chat);
			} else if (chat.getUsers().contains(reqUser)) {
				if (user.getId().equals(reqUser.getId())) {
					chat.getUsers().remove(user);

					return chatRepository.save(chat);
				}
				
			}
				throw new UserException("You cannot remove another user ");

		}

		throw new ChatException("Chat not found with chat id " + chatId);
	}

	@Override
	public void deleteChat(Integer chatId, Integer userId) throws ChatException, UserException {
		Optional<Chat> opt=chatRepository.findById(chatId);
		
		if(opt.isPresent()) {
			Chat chat=opt.get();
			chatRepository.deleteById(chat.getId());
		}

	}

	@Override
	public List<Chat> findAllChatByUserId(Integer userId) throws UserException {
		User user = userService.findUserById(userId);

		List<Chat> chats = chatRepository.findChatByUserId(user.getId());

		return chats;
	}

}
