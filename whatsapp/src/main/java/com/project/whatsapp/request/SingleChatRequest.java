package com.project.whatsapp.request;

public class SingleChatRequest {

	private Integer userId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public SingleChatRequest() {
		super();

	}
	public SingleChatRequest(Integer userId) {
		super();
		this.userId = userId;
	}

}
