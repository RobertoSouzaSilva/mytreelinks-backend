package com.robertosouza.mytreelinks.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.robertosouza.mytreelinks.entity.LinksEntity;
import com.robertosouza.mytreelinks.entity.RegraEntity;
import com.robertosouza.mytreelinks.entity.UsuarioEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;
	private String email;
	private String apelidoUrl;
	private String urlImg;
	//private List<RegraDTO> regraDTO = new ArrayList<>();
	private List<LinksDTO> linksDTO = new ArrayList<>();

	public UsuarioDTO(UsuarioEntity usuario) {
		this.id = usuario.getId();
		this.nome = usuario.getNome();
		this.email = usuario.getEmail();
		this.apelidoUrl = usuario.getApelidoUrl();
		this.urlImg = usuario.getUrlImg();
	}

	public UsuarioDTO(UsuarioEntity usuario, List<LinksEntity> links) {
		this(usuario);
		links.forEach(link -> linksDTO.add(new LinksDTO(link)));
	}

}
