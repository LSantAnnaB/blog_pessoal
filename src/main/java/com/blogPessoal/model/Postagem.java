package com.blogPessoal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_postagem")
public class Postagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("postagem")
    @JoinColumn(name = "tema_id")
    private Tema tema;

    @NotBlank(message = "titulo nao pode ser varios nada")
    @Size(min = 5,max = 100, message = "Digite um titulo entre 5 e 100 caracteres")
    private String titulo;

    @NotBlank(message = "titulo nao pode ser varios nada")
    @Size(min = 10,max = 1000, message = "Digite um texto entre 10 e 1000 caracteres")
    private String texto;

    private LocalDateTime data;

    public Tema getTema() {
        return tema;
    }

    @ManyToOne
    @JsonIgnoreProperties("postagem")
    private Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setTema(Tema tema) {
        this.tema = tema;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }
}
