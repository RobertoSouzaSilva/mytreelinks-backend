package com.robertosouza.mytreelinks.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.robertosouza.mytreelinks.dto.LinksDTO;
import com.robertosouza.mytreelinks.dto.UsuarioDTO;
import com.robertosouza.mytreelinks.dto.UsuarioInsertDTO;
import com.robertosouza.mytreelinks.service.UsuarioService;

@RestController
@RequestMapping(value = "/users")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping
	public ResponseEntity<List<UsuarioDTO>> findAll() {
		List<UsuarioDTO> dto = usuarioService.findAll();
		return ResponseEntity.ok().body(dto);

	}

	@GetMapping(value = "/{apelido}")
	public ResponseEntity<UsuarioDTO> findByName(@PathVariable String apelido) {
		UsuarioDTO dto = usuarioService.findByApelidoUrl(apelido);
		return ResponseEntity.ok().body(dto);
	}

	@PostMapping
	public ResponseEntity<UsuarioDTO> insert(@RequestBody UsuarioInsertDTO dto) {
		UsuarioDTO usuarioDto = usuarioService.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(usuarioDto);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<UsuarioDTO> update(@PathVariable Long id, @RequestBody UsuarioInsertDTO dto) {
		UsuarioDTO usuarioDto = usuarioService.update(id, dto);
		return ResponseEntity.ok().body(usuarioDto);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<UsuarioDTO> delete(@PathVariable Long id){
		usuarioService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
