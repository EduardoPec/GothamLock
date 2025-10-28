package com.wayne.waynesecurity.dto;

import java.io.Serializable;

import com.wayne.waynesecurity.model.User;
import com.wayne.waynesecurity.model.enums.Role;

public class UserDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
    private String name;
    private String email;
    private Role role; 
	
	public UserDTO() {
    }
	
	public UserDTO(User user) {
		this.id = user.getId();
		this.name = user.getName();
        this.email = user.getEmail();
        this.role = user.getRole();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}