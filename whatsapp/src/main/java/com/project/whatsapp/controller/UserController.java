package com.project.whatsapp.controller;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.project.whatsapp.Exception.UserException;
import com.project.whatsapp.Response.ApiResponse;
import com.project.whatsapp.Service.UserService;
import com.project.whatsapp.modal.User;
import com.project.whatsapp.request.UpdateUserRequest;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/profile")
	public ResponseEntity<User> getUserProfileHandler(@RequestHeader("Authorization") String token)
			throws UserException {
		User user = userService.findUserProfile(token);
		return new ResponseEntity<User>(user, HttpStatus.ACCEPTED);
	}

	@GetMapping("/{query}")
	public ResponseEntity<List<User>> searchUserHandler(@PathVariable("query") String query) {

		List<User> users = userService.searchUser(query);
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<ApiResponse> updateUserHandler(@RequestBody UpdateUserRequest req,
			@RequestHeader("Authorization") String token) throws UserException { 
		User user = userService.findUserProfile(token);
		userService.updateUser(user.getId(), req);
		ApiResponse apiResponse = new ApiResponse("User Updated Successfully", true);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/search")
	public ResponseEntity<HashSet<User>>searchUsersByName(@RequestParam("name") String name){
		int i=1;
		System.out.println( i++ );
		List<User>users=userService.searchUser(name);
		HashSet<User>set=new HashSet<>(users);
		return new ResponseEntity<>(set,HttpStatus.ACCEPTED);
	}
	
}
