package com.wayne.waynesecurity.model.dto;

import com.wayne.waynesecurity.model.User;
import com.wayne.waynesecurity.model.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public class UserRequestDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    private String name;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    private String password;

    @NotNull(message = "Papel do usuário é obrigatório")
    private Role role; 
	
	public UserRequestDTO() {
    }

    public User toEntity() {
        User user = new User();
        user.setId(this.id);
        user.setName(this.name);
        user.setEmail(this.email);
        user.setRole(this.role);
        user.setPassword(this.password);
        return user;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
	}

	public void setRole(Role role) {
        this.role = role;
	}
}