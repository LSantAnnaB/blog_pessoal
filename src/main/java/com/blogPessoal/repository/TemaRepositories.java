package com.blogPessoal.repository;

import com.blogPessoal.model.Postagem;
import com.blogPessoal.model.Tema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TemaRepositories extends JpaRepository<Tema,Long> {

    public List<Tema> findByDescricaoContainingIgnoreCase (@Param("descricao") String descricao);


    public Optional<Tema> findById(Long id);

}
