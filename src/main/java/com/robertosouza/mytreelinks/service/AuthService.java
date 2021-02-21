package com.robertosouza.mytreelinks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.robertosouza.mytreelinks.entity.UsuarioEntity;
import com.robertosouza.mytreelinks.exceptions.ForbiddenException;
import com.robertosouza.mytreelinks.exceptions.UnauthorizedException;
import com.robertosouza.mytreelinks.repository.UsuarioRepository;

@Service
public class AuthService {
	
	@Autowired
	private UsuarioRepository usuariorRepository;

	@Transactional(readOnly = true)
	public UsuarioEntity authenticaded() {
		try {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			return usuariorRepository.findByEmail(username);
		} catch (Exception e) {
			throw new UnauthorizedException("Invalid User");
		}
	}
	
	public void validateSelf(Long userId) {
		UsuarioEntity usuario = authenticaded();

		if(!usuario.getId().equals(userId)) {
			throw new ForbiddenException("Access denied");
		}
	}

}
