package com.robertosouza.mytreelinks.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.robertosouza.mytreelinks.dto.LinksDTO;
import com.robertosouza.mytreelinks.dto.RegraDTO;
import com.robertosouza.mytreelinks.dto.UsuarioDTO;
import com.robertosouza.mytreelinks.dto.UsuarioInsertDTO;
import com.robertosouza.mytreelinks.entity.LinksEntity;
import com.robertosouza.mytreelinks.entity.RegraEntity;
import com.robertosouza.mytreelinks.entity.UsuarioEntity;
import com.robertosouza.mytreelinks.exceptions.UsuarioNotFoundException;
import com.robertosouza.mytreelinks.repository.LinksRepository;
import com.robertosouza.mytreelinks.repository.RegraRepository;
import com.robertosouza.mytreelinks.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private LinksRepository linkRepository;
	
	@Autowired
	private RegraRepository regraRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Transactional(readOnly = true)
	public List<UsuarioDTO> findAll() {
		List<UsuarioEntity> links = usuarioRepository.findAll();
		return links.stream().map(usu -> new UsuarioDTO(usu, usu.getLinks(), usu.getRegras())).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public UsuarioDTO findByApelidoUrl(String apelido_url) {
		UsuarioEntity usuario = usuarioRepository.findByApelidoUsuario(apelido_url);
		if(usuario == null) {
			throw new UsuarioNotFoundException("Usuário não encontrado!");
		}
		//UsuarioEntity links = usuarioRepository.buscaPorId(usuario.getId());
		return new UsuarioDTO(usuario, usuario.getLinks(), usuario.getRegras());
	}

	@Transactional
	public UsuarioDTO insert(UsuarioInsertDTO dto) {
		UsuarioEntity usuario = new UsuarioEntity();
		usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
		copyDtoToEntity(dto, usuario);
		usuario = usuarioRepository.save(usuario);	
		return new UsuarioDTO(usuario);
	}
	
	@Transactional
	public UsuarioDTO update(Long id, UsuarioInsertDTO dto) {
		try {
		UsuarioEntity usuario = usuarioRepository.getOne(id);
		copyDtoToEntity(dto, usuario);
		usuario = usuarioRepository.save(usuario);
		return new UsuarioDTO(usuario);
		} catch(EntityNotFoundException e ) {
			throw new UsuarioNotFoundException("Id não encontrado " + id);
		}
	}
	
	public void delete(Long id) {
		try {
			usuarioRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new UsuarioNotFoundException("Id não encontrado " + id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Violação de integridade");
		}
	}
	
	private void copyDtoToEntity(UsuarioDTO dto, UsuarioEntity usuario) {
		usuario.setNome(dto.getNome());
		usuario.setEmail(dto.getEmail());
		usuario.setApelidoUrl(dto.getApelidoUrl());
		
		usuario.getRegras().clear();
		usuario.getLinks().clear();
		
		for(RegraDTO regraDto : dto.getRegraDTO()) {
			RegraEntity regra = regraRepository.getOne(regraDto.getId());
			usuario.getRegras().add(regra);
		}
		
		for(LinksDTO linkDto : dto.getLinksDTO()) {
			LinksEntity link = linkRepository.getOne(linkDto.getId());
			usuario.getLinks().add(link);
			
		}
		
	}
	

	

	
}
