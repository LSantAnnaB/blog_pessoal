package com.blogPessoal.controller;

import com.blogPessoal.model.Usuario;
import com.blogPessoal.repository.UsuarioRepositories;
import com.blogPessoal.service.UsuarioService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioControllerTest {

    @Autowired
    TestRestTemplate testRestTemplate;
    @Autowired
    UsuarioRepositories usuarioRepositories;

    @Autowired
    UsuarioService usuarioService;

    @BeforeAll
    void start() {
        usuarioRepositories.deleteAll();
        usuarioService.cadastrarUsuario
                (new Usuario(0L, "Root", "root@root.com", "rootroot", " "));
    }


    @Test
    @DisplayName("Cadastrar um usuario")
    public void deveCriarUmUsuario() {

        HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>
                (new Usuario(0L, "nome teste", "testeemail@email.com", "123456789", "http://i.imgur.com/FETvs20.jpg"));

        ResponseEntity<Usuario> corpoResposta = testRestTemplate.
                exchange("/usuarios/cadastrar", HttpMethod.POST, corpoRequisicao, Usuario.class);

        assertEquals(HttpStatus.CREATED, corpoResposta.getStatusCode());
        assertEquals(corpoRequisicao.getBody().getNome(), corpoResposta.getBody().getNome());
        assertEquals(corpoRequisicao.getBody().getUsuario(), corpoResposta.getBody().getUsuario());

    }

    @Test
    @DisplayName("Nao deve Permitir usuario duplicado")
    public void naoDeveDuplicarUsuario() {

        usuarioService.cadastrarUsuario(new Usuario(0L, "nome 1", "teste1@email.com", "123456789", "http://i.imgur.com/FETvs20.jpg"));

   HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(
           new Usuario(0L, "nome 1", "teste1@email.com", "123456789", "http://i.imgur.com/FETvs20.jpg"));

   ResponseEntity<Usuario> corpoResposta = testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST, corpoRequisicao, Usuario.class);

   assertEquals(HttpStatus.BAD_REQUEST,corpoResposta.getStatusCode());

    }

    @Test
    @DisplayName("Atualizar um usuario")
    public void deveAtuaizarUmUsuario() {
        Optional<Usuario> usuarioCadastrado = usuarioService.cadastrarUsuario(new Usuario
                (0L, "nome teste", "testeemail@email.com", "123456789", "http://i.imgur.com/FETvs20.jpg"));

        Usuario usuarioUpdate = new Usuario(usuarioCadastrado.get().getId(),
                "nome teste atualizado", "testeemail@email.com", "123456789", "http://i.imgur.com/FETvs20.jpg");

        HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario> (usuarioUpdate);

        ResponseEntity<Usuario> corpoResposta = testRestTemplate.
                withBasicAuth("root@root.com", "rootroot")
                .exchange("/usuarios/atualizar", HttpMethod.PUT, corpoRequisicao, Usuario.class);

        assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());
        assertEquals(corpoRequisicao.getBody().getNome(), corpoResposta.getBody().getNome());
        assertEquals(corpoRequisicao.getBody().getUsuario(), corpoResposta.getBody().getUsuario());

    }



    @Test
    @DisplayName("Listar todos os usuarios")
    public void deveListarTodosUsuarios() {

        usuarioService.cadastrarUsuario((new Usuario(0L, "nome 1", "teste1@email.com", "123456789", "http://i.imgur.com/FETvs20.jpg")));
        usuarioService.cadastrarUsuario((new Usuario(0L, "nome 2", "teste2@email.com", "123456789", "http://i.imgur.com/FETvs20.jpg")));


        ResponseEntity<String> resposta = testRestTemplate.
                withBasicAuth("root@root.com", "rootroot")
                .exchange("/usuarios/all", HttpMethod.GET, null, String.class);

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
    }

    @AfterAll
    public void end(){
        usuarioRepositories.deleteAll();
    }

}
