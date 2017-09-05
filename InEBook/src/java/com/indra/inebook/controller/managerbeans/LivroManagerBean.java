/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.indra.inebook.controller.managerbeans;

import com.indra.inebook.model.vo.Livro;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author guilherme
 */
@ManagedBean(name = "livroBean")
@SessionScoped
public class LivroManagerBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Livro> livros;
    private String consultaName;
    private String tipoConsulta;

    @PostConstruct
    public void init() {
        this.livros = new ArrayList<Livro>();

        for (int i = 0; i < 5; i++) {
            this.livros.add(new Livro(i, "nomelivro", "autor livo", (float) 55.5, "3b"));
        }

    }

    public void getBuscarLivros() {

        for (int i = 0; i < 5; i++) {
            this.livros.add(new Livro(i, "nomelivro", "autor livo", (float) 55.5, "3b"));
        }
        
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }

    public String getConsultaName() {
        return consultaName;
    }

    public void setConsultaName(String consultaName) {
        this.consultaName = consultaName;
    }

    public String getTipoConsulta() {
        return tipoConsulta;
    }

    public void setTipoConsulta(String tipoConsulta) {
        this.tipoConsulta = tipoConsulta;
    }

}
