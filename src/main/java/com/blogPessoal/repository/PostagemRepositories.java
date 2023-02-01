package com.blogPessoal.repository;

import com.blogPessoal.model.Postagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostagemRepositories extends JpaRepository<Postagem,Long> {

    public List<Postagem> findByTituloContainingIgnoreCase (String titulo);

    public Optional<Postagem> findById(Long id);

    public List<Postagem> saveAll();


}


