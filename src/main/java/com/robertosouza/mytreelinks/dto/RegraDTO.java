package com.robertosouza.mytreelinks.dto;

import java.io.Serializable;

import com.robertosouza.mytreelinks.entity.RegraEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegraDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String regra;
	
	public RegraDTO(RegraEntity entity) {
		id = entity.getId();
		regra = entity.getRegra();
	}
}
