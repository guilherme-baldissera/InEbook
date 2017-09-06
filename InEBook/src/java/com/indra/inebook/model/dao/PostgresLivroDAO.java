/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.indra.inebook.model.dao;


import com.indra.inebook.model.vo.Livro;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author guilherme
 */
public class PostgresLivroDAO implements LivroDAO {

    private static final String DRIVER = "org.postgresql.Driver";
    private static final String DBURL = "jdbc:postgresql://LOCALHOST:5432/InEbook";
    private static final String USER = "postgres";
    private static final String SENHA = "25041993";
    
    private static final String SQL_LIVROS_BY_NOME = "SELECT LR.ID,LR.NOME,LR.AUTOR,LE.PRECO,LE.SECCAO FROM LIVROS_REGISTRADOS AS LR, LIVROS_ESTOQUE AS LE WHERE LR.ID = LE.ID_REG AND LR.NOME LIKE ?";
    private static final String SQL_LIVROS_BY_AUTOR = "SELECT LR.ID,LR.NOME,LR.AUTOR,LE.PRECO,LE.SECCAO FROM LIVROS_REGISTRADOS AS LR, LIVROS_ESTOQUE AS LE WHERE LR.ID = LE.ID_REG AND LR.AUTOR LIKE ?";

    
    
    public List<Livro> getLivroByNome(String livro) {
        List<Livro> livros = new ArrayList<Livro>();
        try(Connection cnn = getConnection()){
            try(PreparedStatement ps = cnn.prepareStatement(SQL_LIVROS_BY_NOME)){
            ps.setString(1, (livro+"%"));
                try (ResultSet rs = ps.executeQuery()){
                    while(rs.next()){
                        livros.add(new Livro(rs.getInt(1), rs.getString(2), rs.getString(3),rs.getFloat(4), rs.getString(5)));
                    }      
                } catch (SQLException sql) {
                    System.out.println(sql.getMessage());
                    sql.printStackTrace(System.err);
                }
            }catch(SQLException sql){
                System.out.println(sql.getMessage());
                sql.printStackTrace(System.err);
            }
          
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace(System.err);
            
        }
            return livros;
        }
    public List<Livro> getLivroByAutor(String autor) {
        List<Livro> livros = new ArrayList<Livro>();
        try(Connection cnn = getConnection()){
            try(PreparedStatement ps = cnn.prepareStatement(SQL_LIVROS_BY_AUTOR)){
            ps.setString(1, (autor+"%"));
                try (ResultSet rs = ps.executeQuery()){
                    while(rs.next()){
                        livros.add(new Livro(rs.getInt(1), rs.getString(2), rs.getString(3),rs.getFloat(4), rs.getString(5)));
                    }      
                } catch (SQLException sql) {
                    System.out.println(sql.getMessage());
                    sql.printStackTrace(System.err);
                }
            }catch(SQLException sql){
                System.out.println(sql.getMessage());
                sql.printStackTrace(System.err);
            }
          
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace(System.err);
            
        }


            return livros;
        }

    public Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(DBURL, USER, SENHA);

        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return conn;
    }
}
