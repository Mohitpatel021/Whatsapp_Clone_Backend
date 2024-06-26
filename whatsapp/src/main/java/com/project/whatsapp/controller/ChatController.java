
package com.project.whatsapp.controller;

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

import com.project.whatsapp.Exception.ChatException;
import com.project.whatsapp.Exception.UserException;
import com.project.whatsapp.Response.ApiResponse;
import com.project.whatsapp.Service.ChatService;
import com.project.whatsapp.Service.UserService;
import com.project.whatsapp.modal.Chat;
import com.project.whatsapp.modal.User;
import com.project.whatsapp.request.GroupChatRequest;
import com.project.whatsapp.request.SingleChatRequest;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

	private ChatService chatService;

	private UserService userService;

	public ChatController(ChatService chatService, UserService userService) {
		this.chatService = chatService;
		this.userService = userService;
	}

	@PostMapping("/single")
	public ResponseEntity<?> createChatHandler(@RequestBody SingleChatRequest singleChatRequest,
			@RequestHeader("Authorization") String jwt) throws UserException {
		User reqUser = userService.findUserProfile(jwt);
		Chat chat = chatService.createChat(reqUser, singleChatRequest.getUserId());
		return new ResponseEntity<>(chat, HttpStatus.OK);
	}

	@PostMapping("/group")
	public ResponseEntity<?> createGroupHandler(@RequestBody GroupChatRequest req,
			@RequestHeader("Authorization") String jwt) throws UserException, ChatException {
		User reqUser = userService.findUserProfile(jwt);
		Chat chat = chatService.createGroup(req, reqUser);
		return new ResponseEntity<>(chat, HttpStatus.OK);
	}

	@GetMapping("/{chatId}")
	public ResponseEntity<?> findChatByIdHandler(@PathVariable Integer chatId,
			@RequestHeader("Authorization") String jwt) throws UserException, ChatException {
		Chat chat = chatService.findChatById(chatId);
		return new ResponseEntity<>(chat, HttpStatus.OK);
	}

	@GetMapping("/user")
	public ResponseEntity<?> FindAllChatByUserIdHandler(@RequestHeader("Authorization") String jwt)
			throws UserException, ChatException {
		User reqUser = userService.findUserProfile(jwt);
		List<Chat> chat = chatService.findAllChatByUserId(reqUser.getId());
		return new ResponseEntity<>(chat, HttpStatus.OK);
	}

	@PutMapping("/{chatId}/add/{userId}")
	public ResponseEntity<?> addUserToGroupHandler(@PathVariable Integer chatId, @PathVariable Integer userId,
			@RequestHeader("Authorization") String jwt) throws UserException, ChatException {
		User reqUser = userService.findUserProfile(jwt);
		Chat chat = chatService.addUserToGroup(userId, chatId, reqUser);
		return new ResponseEntity<>(chat, HttpStatus.OK);
	}

	@PutMapping("/{chatId}/remove/{userId}")
	public ResponseEntity<?> removeUserFromGroupHandler(@PathVariable Integer chatId, @PathVariable Integer userId,
			@RequestHeader("Authorization") String jwt) throws UserException, ChatException {
		User reqUser = userService.findUserProfile(jwt);
		Chat chat = chatService.removeFromGroup(chatId, userId, reqUser);
		return new ResponseEntity<>(chat, HttpStatus.OK);
	}

	@DeleteMapping("delete/{chatId}")
	public ResponseEntity<?> deleteChatHandler(@PathVariable Integer chatId, @RequestHeader("Authorization") String jwt)
			throws UserException, ChatException {
		User reqUser = userService.findUserProfile(jwt);
		chatService.deleteChat(chatId, reqUser.getId());
		ApiResponse apiResponse = new ApiResponse("Chat Deleted Successful ", true);
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}

}
