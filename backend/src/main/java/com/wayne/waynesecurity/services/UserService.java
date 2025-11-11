package com.wayne.waynesecurity.services;

import com.wayne.waynesecurity.model.User;
import com.wayne.waynesecurity.model.enums.AccessArea;
import com.wayne.waynesecurity.repositories.UserRepository;
import com.wayne.waynesecurity.services.exceptions.DatabaseException;
import com.wayne.waynesecurity.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

	private final UserRepository repository;
    private final AccessControlService accessControl;
    private final PasswordEncoder passwordEncoder;

    public UserService(AccessControlService accessControl, UserRepository repository, PasswordEncoder passwordEncoder) {
        this.accessControl = accessControl;
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll() {
		return repository.findAll();
	}
	
	public User findById(Long id) {
		Optional<User> user = repository.findById(id);
		return user.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public User insert(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return repository.save(user);
	}
	
	public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(id);
        }
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
	
	public User update(Long id, User user) {
		try {
			User entity = repository.getReferenceById(id);
			updateData(entity, user);
			return repository.save(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	private void updateData(User entity, User user) {
		entity.setName(user.getName());
		entity.setEmail(user.getEmail());
		entity.setRole(user.getRole());
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
