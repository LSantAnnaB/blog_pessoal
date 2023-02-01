package com.blogPessoal.controller;

import com.blogPessoal.model.Postagem;
import com.blogPessoal.repository.PostagemRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = {"/postagens"})
@CrossOrigin("*")
public class PostagemContoller {

    @Autowired
    private PostagemRepositories repositories;

    @GetMapping
    public ResponseEntity<List<Postagem>>  getAll(){
            return ResponseEntity.ok(repositories.findAll());
    }
}
