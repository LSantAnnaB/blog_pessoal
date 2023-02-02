package com.blogPessoal.repository;

import com.blogPessoal.model.Postagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostagemRepositories extends JpaRepository<Postagem,Long> {

    public List<Postagem> findByTituloContainingIgnoreCase (@Param("titulo") String titulo);

    public List<Postagem> findByTextoContainingIgnoreCase (@Param("texto") String texto);


    public Optional<Postagem> findById(Long id);




}
