package com.blogPessoal.repository;

import com.blogPessoal.model.Usuario;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.AfterTestClass;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {

    @Autowired
       private UsuarioRepositories usuarioRepositories;

    @BeforeAll
        void start(){
        usuarioRepositories.deleteAll();
        usuarioRepositories.save(new Usuario(0L,"nome 1","teste1@email.com","123456789","http://i.imgur.com/FETvs20.jpg"));
        usuarioRepositories.save(new Usuario(0L,"nome 2","teste2@email.com","123456789","http://i.imgur.com/FETvs20.jpg"));
        usuarioRepositories.save(new Usuario(0L,"nome 3","teste3@email.com","123456789","http://i.imgur.com/FETvs20.jpg"));
        usuarioRepositories.save(new Usuario(0L,"nome 4","teste4@email.com","123456789","http://i.imgur.com/FETvs20.jpg"));
    }

    @Test
    @DisplayName("Retorna 1 usuario")
    public void deveRetornarUmUsuario(){
        Optional<Usuario> usuario = usuarioRepositories.findByUsuario("teste1@email.com");
        assertTrue(usuario.get().getUsuario().equals("teste1@email.com"));
    }

    @Test
    @DisplayName("Retorna 4 usuarios")
    public void deveRetornarTresUsuarios(){
        List<Usuario> listaDeUsuario = usuarioRepositories.findAllByNomeContainingIgnoreCase("nome");
        assertEquals(4,listaDeUsuario.size());
        assertTrue(listaDeUsuario.get(0).getNome().equals("nome 1"));
        assertTrue(listaDeUsuario.get(1).getNome().equals("nome 2"));
        assertTrue(listaDeUsuario.get(2).getNome().equals("nome 3"));
        assertTrue(listaDeUsuario.get(3).getNome().equals("nome 4"));

    }

    @AfterAll
    public void end(){
        usuarioRepositories.deleteAll();
    }


}
