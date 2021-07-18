package com.apimarvel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apimarvel.entities.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>  {

	Usuario findByEmail(String email);
	Usuario findByCpf(String cpf);
}
