package com.isac.infrastructure.repository;


import com.isac.infrastructure.entity.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {



    boolean existsByEmail(String email); // verifica se o email existe no bd

    Optional<Usuario> findByEmail(String email);

    @Transactional
    void deleteByEmail(String email);
}
