package com.project.whatsapp.Service;

import java.util.List;

import com.project.whatsapp.Exception.ChatException;
import com.project.whatsapp.Exception.MessageExcetpion;
import com.project.whatsapp.Exception.UserException;
import com.project.whatsapp.modal.Message;
import com.project.whatsapp.modal.User;
import com.project.whatsapp.request.SendMessageRequest;

public interface MessageService {

	public Message sendMessage(SendMessageRequest req) throws UserException, ChatException;

	public List<Message> getChatMessages(Integer chatId, User reqUser) throws ChatException, UserException;

	public Message findMessageById(Integer messageId) throws MessageExcetpion;

	public void deleteMessage(Integer messageId, User reqUser) throws MessageExcetpion, UserException;
}
