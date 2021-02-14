package com.robertosouza.mytreelinks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.robertosouza.mytreelinks.entity.UsuarioEntity;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

	@Query(value = "select * from tb_usuario where apelido_url like %:apelido_url%", nativeQuery = true)
	UsuarioEntity findByApelidoUsuario(@Param("apelido_url") String apelido_url);
	


}
