package com.blogPessoal.controller;

import com.blogPessoal.model.Postagem;
import com.blogPessoal.repository.PostagemRepositories;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = {"/postagens"})
@CrossOrigin("*")
public class PostagemContoller {

    @Autowired
    private PostagemRepositories repositories;

    @GetMapping
    public ResponseEntity<List<Postagem>> getAll() {
        return ResponseEntity.ok(repositories.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Postagem> getById(@PathVariable Long id) {
        return repositories.findById(id)
                .map(resposta-> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/titullo/{titulo}")
    public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo) {
        return ResponseEntity.ok(repositories.findByTituloContainingIgnoreCase(titulo));
    }

    @GetMapping("/texto/{texto}")
    public ResponseEntity<List<Postagem>> getByTexto(@PathVariable String texto) {
        return ResponseEntity.ok(repositories.findByTituloContainingIgnoreCase(texto));
    }

    @PostMapping
        public ResponseEntity<Postagem> post(@Valid @RequestBody Postagem postagem){
        return ResponseEntity.status(HttpStatus.CREATED).body(repositories.save(postagem));
    }

    @PutMapping
    public ResponseEntity<Postagem> put(@Valid @RequestBody Postagem postagem){
        return repositories.findById(postagem.getId())
                .map(resposta-> ResponseEntity.status(HttpStatus.OK).body(repositories.save(postagem)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Optional<Postagem> postagem = repositories.findById(id);
            if (postagem.isEmpty())
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            repositories.deleteById(id);

    }
}

