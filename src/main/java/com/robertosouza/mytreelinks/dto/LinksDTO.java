package com.robertosouza.mytreelinks.dto;

import java.io.Serializable;

import com.robertosouza.mytreelinks.entity.LinksEntity;
import com.robertosouza.mytreelinks.entity.UsuarioEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinksDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String url;
	private String textoBotao;
		
	public LinksDTO(LinksEntity links) {
		this.id = links.getId();
		this.url = links.getUrl();
		this.textoBotao = links.getTextoBotao();
	}
	
}
