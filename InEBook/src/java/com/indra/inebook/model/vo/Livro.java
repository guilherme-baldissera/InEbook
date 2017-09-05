/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.indra.inebook.model.vo;

/**
 *
 * @author guilherme
 */
public class Livro {

    private Integer id;
    private String nome;
    private String autor;
    private Float preco;
    private String Seccao;
    
    public Livro(){
        
    }

    public Livro(Integer id, String nome, String autor, Float preco, String Seccao) {
        this.id = id;
        this.nome = nome;
        this.autor = autor;
        this.preco = preco;
        this.Seccao = Seccao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Float getPreco() {
        return preco;
    }

    public void setPreco(Float preco) {
        this.preco = preco;
    }

    public String getSeccao() {
        return Seccao;
    }

    public void setSeccao(String Seccao) {
        this.Seccao = Seccao;
    }
}
