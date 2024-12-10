package com.example.demo.Dto;

import jakarta.validation.constraints.NotBlank;

public class AdminLoginRequest {
		@NotBlank(message = "Username field cannot be empty!")
	    private String username;

	    
	    @NotBlank(message="password field cannot be empty!")
	   	private String password;


	    public String getusername() {
	        return username;
	    }

	    public void setAdminUsername(String username) {
	        this.username = username;
	    }

	    public String getpassword() {
	        return password;
	    }

	    public void setAdminPassword(String password) {
	        this.password = password;
	    }
}
