/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.indra.inebook.model.dao;

import com.indra.inebook.model.vo.Livro;
import java.util.List;

/**
 *
 * @author guilherme
 */
public interface LivroDAO {
    public List<Livro> getLivroByNome(String livro);
    public List<Livro> getLivroByAutor(String autor);
    
    
}
