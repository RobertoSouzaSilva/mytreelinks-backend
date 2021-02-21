package com.robertosouza.mytreelinks.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.robertosouza.mytreelinks.dto.LinksDTO;
import com.robertosouza.mytreelinks.dto.LinksResponseDTO;
import com.robertosouza.mytreelinks.entity.LinksEntity;
import com.robertosouza.mytreelinks.entity.UsuarioEntity;
import com.robertosouza.mytreelinks.exceptions.LinkNotFoundException;
import com.robertosouza.mytreelinks.exceptions.UsuarioNotFoundException;
import com.robertosouza.mytreelinks.repository.LinksRepository;
import com.robertosouza.mytreelinks.repository.UsuarioRepository;


@Service
public class LinksService {

	@Autowired
	private LinksRepository linksRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private AuthService authService;

//	@Transactional(readOnly = true)
//	public List<LinksDTO> findAll() {
//		List<LinksEntity> links = linksRepository.findAll();
//		return links.stream().map(x -> new LinksDTO(x)).collect(Collectors.toList());
//	}

	@Transactional(readOnly = true)
	public List<LinksDTO> findByApelidoUrl(String apelido) {
		UsuarioEntity usuario = usuarioRepository.findByApelidoUsuario(apelido);
		if (usuario == null) {
			throw new UsuarioNotFoundException("Usuário não encontrado!");
		}
		List<LinksEntity> links = linksRepository.findByUsuarioId(usuario.getId());
		return links.stream().map(link -> new LinksDTO(link)).collect(Collectors.toList());
	}

	@Transactional
	public LinksDTO insert(LinksResponseDTO dto) {
		LinksEntity links = new LinksEntity();
		copyDtoToEntitySave(dto, links);
		links = linksRepository.save(links);
		return new LinksDTO(links);
	}

	@Transactional
	public LinksDTO update(Long id, LinksResponseDTO dto) {
		try {
			LinksEntity links = new LinksEntity();
			copyDtoToEntityUpdate(id, links, dto);
			links = linksRepository.save(links);
			return new LinksDTO(links);
		} catch (EntityNotFoundException e) {
			throw new UsuarioNotFoundException("Id não encontrado " + id);
		}
	}

	public void delete(Long id) {
		try {
			Optional<LinksEntity> link = linksRepository.findById(id);
			LinksEntity linksEntity = link.orElseThrow(() -> new LinkNotFoundException("Link não encontrado"));		
			authService.validateSelf(linksEntity.getUsuario().getId());			
			linksRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new UsuarioNotFoundException("Id não encontrado " + id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Violação de integridade");
		}
	}

	private void copyDtoToEntitySave(LinksResponseDTO dto, LinksEntity links) {
		links.setUrl(dto.getUrl());
		links.setTextoBotao(dto.getTextoBotao());
		Optional<UsuarioEntity> usuOpt = usuarioRepository.findById(dto.getUsuario().getId());
		UsuarioEntity usuEnt = usuOpt.orElseThrow(() -> new UsuarioNotFoundException("Usuário não encontrado!"));
		authService.validateSelf(usuEnt.getId());
		links.setUsuario(usuEnt);
	}

	private LinksEntity copyDtoToEntityUpdate(Long id, LinksEntity links, LinksResponseDTO dto) {
		authService.validateSelf(dto.getUsuario().getId());
		Optional<LinksEntity> lnkOpt = linksRepository.findById(id);
		LinksEntity lnkEnt = lnkOpt.orElseThrow(() -> new LinkNotFoundException("Link não encontrado!"));
		links.setId(lnkEnt.getId());
		links.setUrl(dto.getUrl());
		links.setTextoBotao(dto.getTextoBotao());
		links.setUsuario(lnkEnt.getUsuario());
		return links;

	}

}
