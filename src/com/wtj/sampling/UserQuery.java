package com.wtj.sampling;

import java.io.Serializable;

public class UserQuery implements Serializable{

	private static final long serialVersionUID = 1L;
	private String username;
	private String query;
	private int time;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	
	public UserQuery(String username, String query, int time) {
		super();
		this.username = username;
		this.query = query;
		this.time = time;
	}
	
	@Override
	public String toString() {
		return "username:"+username+",query:"+query+",time:"+time;
	}
	
}
