package com.project.whatsapp.controller;

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

import com.project.whatsapp.Exception.ChatException;
import com.project.whatsapp.Exception.MessageExcetpion;
import com.project.whatsapp.Exception.UserException;
import com.project.whatsapp.Response.ApiResponse;
import com.project.whatsapp.Service.MessageService;
import com.project.whatsapp.Service.UserService;
import com.project.whatsapp.modal.Message;
import com.project.whatsapp.modal.User;
import com.project.whatsapp.request.SendMessageRequest;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

	@Autowired
	private MessageService messageService;

	@Autowired
	private UserService userService;

	@PostMapping("/create")
	public ResponseEntity<?> sendMessageHandler(@RequestBody SendMessageRequest req,
			@RequestHeader("Authorization") String jwt) throws UserException, ChatException {
		User user = userService.findUserProfile(jwt);
		req.setUserId(user.getId());
		Message message = messageService.sendMessage(req);
		return new ResponseEntity<>(message, HttpStatus.OK);

	}

	@GetMapping("/chat/{chatId}")
	public ResponseEntity<?> getChatsMessagesHandler(@PathVariable Integer chatId,
			@RequestHeader("Authorization") String jwt) throws UserException, ChatException {
		User user = userService.findUserProfile(jwt);
		List<Message> message = messageService.getChatMessages(chatId, user);
		return new ResponseEntity<>(message, HttpStatus.OK);

	}

	@DeleteMapping("/{messageId}")
	public ResponseEntity<?> deleteMessagesHandler(@PathVariable Integer messageId,
			@RequestHeader("Authorization") String jwt) throws UserException, MessageExcetpion {
		User user = userService.findUserProfile(jwt);
		ApiResponse res = new ApiResponse("Message Deleted Successfully", true);
		messageService.deleteMessage(messageId, user);
		return new ResponseEntity<>(res, HttpStatus.OK);

	}
}
