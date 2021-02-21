package com.robertosouza.mytreelinks.service;

import java.net.URL;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.robertosouza.mytreelinks.dto.LinksDTO;
import com.robertosouza.mytreelinks.dto.RegraDTO;
import com.robertosouza.mytreelinks.dto.UriDTO;
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
public class UsuarioService implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
		
	@Autowired
	private RegraRepository regraRepository;
	
	@Autowired
	private S3Service s3Service;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthService authService;
	
//	@Transactional(readOnly = true)
//	public List<UsuarioDTO> findAll() {
//		List<UsuarioEntity> links = usuarioRepository.findAll();
//		return links.stream().map(usu -> new UsuarioDTO(usu, usu.getLinks(), usu.getRegras())).collect(Collectors.toList());
//	}
	
//	@Transactional(readOnly = true)
//	public UsuarioDTO findByApelidoUrl(String apelido_url) {
//		UsuarioEntity usuario = usuarioRepository.findByApelidoUsuario(apelido_url);
//		if(usuario == null) {
//			throw new UsuarioNotFoundException("Usuário não encontrado!");
//		}
//		//UsuarioEntity links = usuarioRepository.buscaPorId(usuario.getId());
//		authService.validateSelf(usuario.getId());
//
//		return new UsuarioDTO(usuario, usuario.getLinks(), usuario.getRegras());
//	}
	
	@Transactional(readOnly = true)
	public UsuarioDTO findById(Long id) {
		authService.validateSelf(id);
		Optional<UsuarioEntity> userOptional = usuarioRepository.findById(id);			
		UsuarioEntity userEntity = userOptional.orElseThrow(() -> new UsuarioNotFoundException("Usuário não encontrado"));		
		return new UsuarioDTO(userEntity, userEntity.getLinks());	
	}
	

	@Transactional
	public UsuarioDTO insert(UsuarioInsertDTO dto) {
		UsuarioEntity usuario = new UsuarioEntity();
		usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
		copyDtoToEntity(dto, usuario);
		List<RegraEntity> regra = regraRepository.findAll();
		usuario.setRegras(regra);
		usuario = usuarioRepository.save(usuario);	
		return new UsuarioDTO(usuario);
	}
	
	public UriDTO uploadImage(MultipartFile file) {
		URL url = s3Service.uploadImage(file);
		return new UriDTO(url.toString());
	}
	
	@Transactional
	public UsuarioDTO update(Long id, UsuarioInsertDTO dto) {
		try {
		authService.validateSelf(id);
		UsuarioEntity usuario = usuarioRepository.getOne(id);
		copyDtoToEntity(dto, usuario);
		List<RegraEntity> regra = regraRepository.findAll();
		usuario.setRegras(regra);
		usuario = usuarioRepository.save(usuario);
		return new UsuarioDTO(usuario);
		} catch(EntityNotFoundException e ) {
			throw new UsuarioNotFoundException("Id não encontrado " + id);
		}
	}
	
	public void delete(Long id) {
		try {
			authService.validateSelf(id);
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
		usuario.setUrlImg(dto.getUrlImg());		
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UsuarioEntity usuario = usuarioRepository.findByEmail(username);
		if(usuario == null) {
			throw new UsuarioNotFoundException("Email não encontrado");
		}
		return usuario;
	}
	

	

	
}
