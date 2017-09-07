/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.indra.inebook.controller.rest;

import com.google.gson.Gson;
import com.indra.inebook.model.dao.PostgresLivroDAO;
import com.indra.inebook.model.vo.Livro;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;

/**
 * REST Web Service
 *
 * @author guilherme
 */
@Path("WS")
public class ControllerRest {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ControllerRest
     */
    public ControllerRest() {
    }

    /**
     * Retrieves representation of an instance of
     * com.indra.inebook.controller.rest.ControllerRest
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    @Path("getAddLivroJson/{id}/{qtdadd}")
    public String getAddLivroJson(@PathParam("id") Integer id,@PathParam("qtdadd") Integer qtdadd) {
        PostgresLivroDAO pldao = new PostgresLivroDAO();
        Boolean result = false;
        Integer qtd = 0;

        if (pldao.verifyIFExistIDLivro(id)) {
            qtd = pldao.selectQtdLivroByID(id);
            qtd = qtd + qtdadd;
            pldao.updateQtdById(id, qtd);
            result = true;
        }

        Gson g = new Gson();
        return g.toJson(result);
    }

    @GET
    @Produces("application/json")
    @Path("getDelLivroJson/{id}/{qtddel}")
    public String getDelLivroJson(@PathParam("id") Integer id,@PathParam("qtddel") Integer qtddel) {
        PostgresLivroDAO pldao = new PostgresLivroDAO();
        Boolean result = false;
        Integer qtd = 0;

        if (pldao.verifyIFExistIDLivro(id)) {
            qtd = pldao.selectQtdLivroByID(id);
            if (qtd > qtddel) {
                qtd = qtd - qtddel;
                pldao.updateQtdById(id, qtd);
                result = true;
            }
        }

        Gson g = new Gson();
        return g.toJson(result);
    }
    @GET
    @Produces("application/json")
    @Path("getAllLivroJson")
    public String getAllLivroJson() {
        PostgresLivroDAO pldao = new PostgresLivroDAO();
        List<Livro> livros = new ArrayList<Livro>();

        livros = pldao.getAllLivros();

        Gson g = new Gson();
        return g.toJson(livros);
    }

    /**
     * PUT method for updating or creating an instance of ControllerRest
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
}
