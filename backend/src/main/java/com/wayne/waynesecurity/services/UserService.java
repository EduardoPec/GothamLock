package com.wayne.waynesecurity.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wayne.waynesecurity.model.User;
import com.wayne.waynesecurity.model.enums.AccessArea;
import com.wayne.waynesecurity.repositories.UserRepository;
import com.wayne.waynesecurity.services.exceptions.DatabaseException;
import com.wayne.waynesecurity.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
    private AccessControlService accessControl;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	public List<User> findAll() {
		return repository.findAll();
	}
	
	public User findById(Long id) {
		Optional<User> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public User insert(User obj) {
		obj.setPassword(passwordEncoder.encode(obj.getPassword()));
		return repository.save(obj);
	}
	
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		}
		catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	public User update(Long id, User obj) {
		try {
			User entity = repository.getReferenceById(id);
			updateData(entity, obj);
			return repository.save(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	private void updateData(User entity, User obj) {
		entity.setName(obj.getName());
		entity.setEmail(obj.getEmail());
		entity.setRole(obj.getRole());
	}

	public void enterArea(Long id, String area) {
		User user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));

		AccessArea accessArea;
		try {
			accessArea = AccessArea.valueOf(area);
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException("Área de acesso inválida: " + area);
		}

		if (!accessControl.haveAccess(user.getRole(), accessArea)) {
			throw new SecurityException(
					user.getName() + " (Role: " + user.getRole() + ") não pode acessar a área " + area);
		}
		System.out.println("Acesso autorizado a " + user.getName() + " na área " + area);
	}
}
