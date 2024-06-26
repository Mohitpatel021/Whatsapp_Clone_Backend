package com.project.whatsapp.ServiceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.whatsapp.Exception.ChatException;
import com.project.whatsapp.Exception.MessageExcetpion;
import com.project.whatsapp.Exception.UserException;
import com.project.whatsapp.Service.ChatService;
import com.project.whatsapp.Service.MessageService;
import com.project.whatsapp.Service.UserService;
import com.project.whatsapp.modal.Chat;
import com.project.whatsapp.modal.Message;
import com.project.whatsapp.modal.User;
import com.project.whatsapp.repository.MessageRepository;
import com.project.whatsapp.request.SendMessageRequest;
@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageRepository messageRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private ChatService chatService;

	@Override
	public Message sendMessage(SendMessageRequest req) throws UserException, ChatException {

		User user = userService.findUserById(req.getUserId());
		Chat chat = chatService.findChatById(req.getChatId());

		Message message = new Message();
		message.setUser(user);
		message.setChat(chat);
		message.setContent(req.getContent());
		message.setTimeStamp(LocalDateTime.now());

		return message;
	}

	@Override
	public List<Message> getChatMessages(Integer chatId, User reqUser) throws ChatException, UserException {
		Chat chat = chatService.findChatById(chatId);
		if (chat.getUsers().contains(reqUser)) {
			List<Message> messages = messageRepository.findByChatId(chat.getId());
			return messages;
		}
		throw new UserException("You are not Authorised to access these message " + reqUser.getFullName());

	}

	@Override
	public Message findMessageById(Integer messageId) throws MessageExcetpion {
		Optional<Message> opt = messageRepository.findById(messageId);
		if (opt.isPresent()) {
			return opt.get();
		}
		throw new MessageExcetpion("Message not found with this messageID " + messageId);
	}

	@Override
	public void deleteMessage(Integer messageId, User reqUser) throws MessageExcetpion, UserException {

		Message message = findMessageById(messageId);
		if (message.getUser().getId().equals(reqUser.getId())) {
			messageRepository.deleteById(messageId);
		}
		throw new UserException("You can't delete another user's Message");
	}

}
