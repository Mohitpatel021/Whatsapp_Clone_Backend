package com.project.whatsapp.Service;

import java.util.List;

import com.project.whatsapp.Exception.UserException;
import com.project.whatsapp.modal.User;
import com.project.whatsapp.request.UpdateUserRequest;

public interface UserService {

	public User findUserById(Integer id) throws UserException;
	public User findUserProfile(String jwt) throws UserException;
	public User updateUser(Integer userId,UpdateUserRequest req) throws UserException;
	public List<User> searchUser(String query);
	

}
