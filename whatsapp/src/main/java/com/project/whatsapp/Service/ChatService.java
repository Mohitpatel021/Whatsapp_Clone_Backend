package com.project.whatsapp.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.whatsapp.Exception.ChatException;
import com.project.whatsapp.Exception.UserException;
import com.project.whatsapp.modal.Chat;
import com.project.whatsapp.modal.User;
import com.project.whatsapp.request.GroupChatRequest;


public interface ChatService {
	public Chat createChat(User reqUser, Integer chatWithUserId) throws UserException;

	public Chat findChatById(Integer chatId) throws ChatException;

	public List<Chat> findAllChatByUserId(Integer userId) throws UserException;

	public Chat createGroup(GroupChatRequest req, User groupChatRequestUser) throws ChatException, UserException;

	public Chat addUserToGroup(Integer userId, Integer chatId, User reqUser) throws UserException, ChatException;

	public Chat renameGroup(Integer chatId, String groupName, User reqUser) throws UserException, ChatException;

	public Chat removeFromGroup(Integer chatId, Integer userId, User reqUser) throws UserException, ChatException;

	public void deleteChat(Integer chatId, Integer userId) throws UserException, ChatException;
}
