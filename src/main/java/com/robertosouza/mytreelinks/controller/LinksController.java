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
import com.robertosouza.mytreelinks.dto.LinksResponseDTO;
import com.robertosouza.mytreelinks.service.LinksService;

@RestController
@RequestMapping(value = "/links")
public class LinksController {
	
	@Autowired
	private LinksService linksService;
	
	@GetMapping
	public ResponseEntity<List<LinksDTO>> findAll(){
		List<LinksDTO> dto = linksService.findAll();
		return ResponseEntity.ok().body(dto);
		
	}
	
	@GetMapping(value = "/{apelido}")
	public ResponseEntity<List<LinksDTO>> findByName(@PathVariable String apelido){
		List<LinksDTO> dto = linksService.findByApelidoUrl(apelido);
		return ResponseEntity.ok().body(dto);
	}
	
	@PostMapping
	public ResponseEntity<LinksDTO> insert(@RequestBody LinksResponseDTO dto){
		LinksDTO linkDto = linksService.insert(dto);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(dto.getId())
				.toUri();
		return ResponseEntity.created(uri).body(linkDto);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<LinksDTO> update(@PathVariable Long id, @RequestBody LinksResponseDTO dto){
		LinksDTO linkDto = linksService.update(id, dto);
		return ResponseEntity.ok().body(linkDto);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<LinksDTO> delete(@PathVariable Long id){
		linksService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
