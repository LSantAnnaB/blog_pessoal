package com.blogPessoal.controller;

import com.blogPessoal.model.Tema;
import com.blogPessoal.repository.TemaRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = {"/tema"})
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class TemaController {

    @Autowired
    private TemaRepositories temaRepositories;

    @GetMapping
    public ResponseEntity <List<Tema>> getAll(){
        return ResponseEntity.ok(temaRepositories.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Tema> getById(@PathVariable Long id) {
        return temaRepositories.findById(id)
                .map(resposta-> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/tema/{descricao}")
    public ResponseEntity<List<Tema>> getByTitulo(@PathVariable String descricao) {
        return ResponseEntity.ok(temaRepositories.findByDescricaoContainingIgnoreCase(descricao));
    }


    @PostMapping
    public ResponseEntity<Tema> post(@Valid @RequestBody Tema tema){
        return ResponseEntity.status(HttpStatus.CREATED).body(temaRepositories.save(tema));
    }

    @PutMapping
    public ResponseEntity<Tema> put(@Valid @RequestBody Tema tema){
        return temaRepositories.findById(tema.getId())
                .map(resposta-> ResponseEntity.status(HttpStatus.OK).body(temaRepositories.save(tema)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Optional<Tema> tema = temaRepositories.findById(id);
        if (tema.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        temaRepositories.deleteById(id);

    }
}
