package com.wayne.waynesecurity.services;

import com.wayne.waynesecurity.mapper.Imapper;
import com.wayne.waynesecurity.model.User;
import com.wayne.waynesecurity.model.dto.request.UserRequestDTO;
import com.wayne.waynesecurity.model.dto.response.UserResponseDTO;
import com.wayne.waynesecurity.model.enums.AccessArea;
import com.wayne.waynesecurity.repositories.UserRepository;
import com.wayne.waynesecurity.services.exceptions.DatabaseException;
import com.wayne.waynesecurity.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

	private final UserRepository repository;
    private final Imapper mapper;
    private final AccessControlService accessControl;
    private final PasswordEncoder passwordEncoder;

    public UserService(AccessControlService accessControl, UserRepository repository, Imapper mapper, PasswordEncoder passwordEncoder) {
        this.accessControl = accessControl;
        this.repository = repository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toUserResponse)
                .collect(Collectors.toList());
	}
	
	public UserResponseDTO findById(Long id) {
		User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        return mapper.toUserResponse(user);
	}

    @Transactional
	public UserResponseDTO insert(UserRequestDTO requestDTO) {
        User user = mapper.userToEntity(requestDTO);
		user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));

        User saveUser = repository.save(user);
        return mapper.toUserResponse(saveUser);
	}

    @Transactional
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

    @Transactional
	public UserResponseDTO update(Long id, UserRequestDTO requestDTO) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado: " + id));
        updateData(user, requestDTO);

        User saveUpdate = repository.save(user);
        return mapper.toUserResponse(saveUpdate);
	}

	private void updateData(User entity, UserRequestDTO requestDTO) {
		entity.setName(requestDTO.getName());
		entity.setEmail(requestDTO.getEmail());
		entity.setRole(requestDTO.getRole());
	}

	public void enterArea(Long id, String area) {
		User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

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
