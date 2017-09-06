/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.indra.inebook.controller.managerbeans;

import com.indra.inebook.model.dao.PostgresLivroDAO;
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
    private Livro livroSelected;
    private String consultaName;
    private String tipoConsulta;

    @PostConstruct
    public void init() {
        this.livros = new ArrayList<Livro>();

    }

    public void buscarLivros() {

        PostgresLivroDAO pldao = new PostgresLivroDAO();
        if(this.tipoConsulta.equals("1"))
            livros = pldao.getLivroByNome(this.consultaName);
        else if(this.tipoConsulta.equals("2"))
            livros = pldao.getLivroByAutor(this.consultaName);
        
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

    public Livro getLivroSelected() {
        return livroSelected;
    }

    public void setLivroSelected(Livro livroSelected) {
        this.livroSelected = livroSelected;
    }
    

}
