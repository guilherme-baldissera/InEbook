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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

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

    //livros atributos
    private String nomeLivro;
    private String autorLivro;
    private Float precoLivro;
    private Integer quantidadeLivro;
    private String seccaoLivro;

    @PostConstruct
    public void init() {
        this.livros = new ArrayList<Livro>();
        this.livroSelected = null;
    }

    public void alterarLivro() {
        if (!livroSelected.getNome().equals("") && !livroSelected.getAutor().equals("") && !livroSelected.getSeccao().equals("") && (livroSelected.getQuantidade() >= 0) && (livroSelected.getPreco() >= 0)) {
            PostgresLivroDAO pldao = new PostgresLivroDAO();
            pldao.updateLivroRegByID(livroSelected);
            infoAlterado();
        } else {
            errorAlterarCampo();
        }
    }

    public String verifyAlterar() {
        if (livroSelected == null) {
            errorAlterar();
            return "";
        }

        return "alterarlivro";
    }

    public void deletarLivro() {
        PostgresLivroDAO pldao = new PostgresLivroDAO();

        if (livroSelected == null) {
            errorDelete();
        } else {
            pldao.deleteLivroEstByID(livroSelected.getId());
            pldao.deleteLivroRegByID(livroSelected.getId());
            buscarLivros();
            infoDelete();
        }
    }

    public void novoLivro() {
        PostgresLivroDAO pldao = new PostgresLivroDAO();
        Integer id;
        if (!nomeLivro.equals("") && !autorLivro.equals("") && !seccaoLivro.equals("") && (quantidadeLivro >= 0) && (precoLivro >= 0)) {
            if (pldao.verifyIfExistLivro(nomeLivro.toUpperCase(), autorLivro.toUpperCase())) {
                error();
            } else {
                pldao.insertLivroReg(nomeLivro.toUpperCase(), autorLivro.toUpperCase());
                id = pldao.getIdByNomeAutor(nomeLivro.toUpperCase(), autorLivro.toUpperCase());
                pldao.insertLivroEst(quantidadeLivro, seccaoLivro.toUpperCase(), id, precoLivro);
                info();
            }
        } else {
            errorCadastar();
        }
    }

    public void buscarLivros() {

        PostgresLivroDAO pldao = new PostgresLivroDAO();
        if (this.tipoConsulta.equals("1")) {
            livros = pldao.getLivroByNome(this.consultaName.toUpperCase());
        } else if (this.tipoConsulta.equals("2")) {
            livros = pldao.getLivroByAutor(this.consultaName.toUpperCase());
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

    public Livro getLivroSelected() {
        return livroSelected;
    }

    public void setLivroSelected(Livro livroSelected) {
        this.livroSelected = livroSelected;
    }

    public String getNomeLivro() {
        return nomeLivro;
    }

    public void setNomeLivro(String nomeLivro) {
        this.nomeLivro = nomeLivro;
    }

    public String getAutorLivro() {
        return autorLivro;
    }

    public void setAutorLivro(String autorLivro) {
        this.autorLivro = autorLivro;
    }

    public Float getPrecoLivro() {
        return precoLivro;
    }

    public void setPrecoLivro(Float precoLivro) {
        this.precoLivro = precoLivro;
    }

    public Integer getQuantidadeLivro() {
        return quantidadeLivro;
    }

    public void setQuantidadeLivro(Integer quantidadeLivro) {
        this.quantidadeLivro = quantidadeLivro;
    }

    public String getSeccaoLivro() {
        return seccaoLivro;
    }

    public void setSeccaoLivro(String seccaoLivro) {
        this.seccaoLivro = seccaoLivro;
    }

    public void info() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Cadastrado com Sucesso."));
    }

    public void error() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Livro Ja Cadastrado"));
    }

    public void infoDelete() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Deletado com sucesso"));
    }

    public void errorDelete() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Não foi selecionado nenhum livro."));
    }

    public void errorAlterar() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Selecione um livro para alterar a pagina"));
    }

    public void infoAlterado() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Alterado com sucesso"));
    }

    public void errorCadastar() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Existem campos não preenchidos ou invalidos"));
    }

    public void errorAlterarCampo() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Existem campos não preenchidos ou invalidos"));

    }
}
