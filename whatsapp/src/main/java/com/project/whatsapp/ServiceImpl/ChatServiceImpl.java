package com.project.whatsapp.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.whatsapp.Exception.ChatException;
import com.project.whatsapp.Exception.UserException;
import com.project.whatsapp.Service.ChatService;
import com.project.whatsapp.Service.UserService;
import com.project.whatsapp.modal.Chat;
import com.project.whatsapp.modal.User;
import com.project.whatsapp.repository.ChatRepository;
import com.project.whatsapp.request.GroupChatRequest;
@Service
public class ChatServiceImpl implements ChatService {

	@Autowired
	private ChatRepository chatRepository;

	@Autowired
	private UserService userService;

	@Override
	public Chat createChat(User reqUser, Integer chatWithUserId) throws UserException {

		User user = userService.findUserById(chatWithUserId);
		Chat isChatExist = chatRepository.findSingleChatByUserIds(user, reqUser);
	System.out.println("chat is exist or not "+ isChatExist);
		if (isChatExist == null) {
			Chat chat = new Chat();
			chat.setId(chatWithUserId);
			chat.setCreatedBy(reqUser);
			chat.getUsers().add(user);
			chat.getUsers().add(reqUser);
			chat.setGroup(false);
			chatRepository.save(chat);
			return chat;
		}
		System.out.println( "id printing "+isChatExist.getId());
		return isChatExist;

	}

	@Override
	public Chat findChatById(Integer chatId) throws ChatException {
		Optional<Chat> optionalChat = chatRepository.findById(chatId);
		if (optionalChat.isPresent()) {
			return optionalChat.get();
		}
		throw new ChatException("Chat not found with ID: " + chatId);
	}

	@Override
	public List<Chat> findAllChatByUserId(Integer userId) throws UserException {
		User user = userService.findUserById(userId);
		List<Chat> chats = chatRepository.findChatByUserId(user.getId());
		return chats;
	}

	@Override
	public Chat createGroup(GroupChatRequest req, User groupChatRequestUser) throws ChatException, UserException {

		Chat group = new Chat();
		group.setGroup(true);
		group.setChat_image(req.getChat_image());
		group.setChat_name(req.getChat_Name());
		group.setCreatedBy(groupChatRequestUser);
		group.getAdmins().add(groupChatRequestUser);
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
				throw new UserException("You are not authorised to add User ");
			}

		}
		throw new ChatException("Chat not found With ID " + chatId);
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
			throw new UserException("You are not allowed to Rename Group Name ");
		}
		throw new ChatException("Chat not found With ID " + chatId);
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
			throw new UserException("You can't remove the user");
		}
		throw new ChatException("Chat not found With ID " + chatId);
	}

	@Override
	public void deleteChat(Integer chatId, Integer userId) throws UserException, ChatException {
		Optional<Chat> opt = chatRepository.findById(chatId);
		if (opt.isPresent()) {
			Chat chat = opt.get();
			chatRepository.deleteById(chat.getId());
		}
		
	}

}
