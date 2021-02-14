package com.robertosouza.mytreelinks.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinksResponseDTO extends LinksDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private UsuarioDTO usuario;

	
}
