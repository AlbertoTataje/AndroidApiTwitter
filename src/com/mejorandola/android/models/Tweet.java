package com.mejorandola.android.models;


public class Tweet {

	private String id;
	private String name;
	private String screenName;
	private String profileImageUrl;
	private String text;
	private String createdAt;
	
	
	@Override
	public String toString() {
		return "Tweet [name=" + name + ", screenName=" + screenName
				+ ", profileImageUrl=" + profileImageUrl + ", text=" + text
				+ ", createdAt=" + createdAt + "]";
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

}

