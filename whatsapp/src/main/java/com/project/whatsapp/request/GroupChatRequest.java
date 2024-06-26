package com.project.whatsapp.request;

import java.util.List;

public class GroupChatRequest {

	private List<Integer> userIds;
	private String chat_Name;
	private String chat_image;

	public List<Integer> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<Integer> userIds) {
		this.userIds = userIds;
	}

	public String getChat_Name() {
		return chat_Name;
	}

	public void setChat_Name(String chat_Name) {
		this.chat_Name = chat_Name;
	}

	public String getChat_image() {
		return chat_image;
	}

	public void setChat_image(String chat_image) {
		this.chat_image = chat_image;
	}

	public GroupChatRequest(List<Integer> userIds, String chat_Name, String chat_image) {
		super();
		this.userIds = userIds;
		this.chat_Name = chat_Name;
		this.chat_image = chat_image;
	}

	public GroupChatRequest() {
		super();
	}

}
