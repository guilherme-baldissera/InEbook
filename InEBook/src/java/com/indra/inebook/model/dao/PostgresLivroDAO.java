package com.indra.inebook.model.dao;

import com.indra.inebook.model.vo.Livro;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author guilherme
 */
public class PostgresLivroDAO implements LivroDAO {

    private static final String DRIVER = "org.postgresql.Driver";
    private static final String DBURL = "jdbc:postgresql://LOCALHOST:5432/InEbook";
    private static final String USER = "postgres";
    private static final String SENHA = "25041993";
    //selecao
    private static final String SQL_LIVROS_BY_NOME = "SELECT LR.ID,LR.NOME,LR.AUTOR,LE.PRECO,LE.SECCAO,LE.QUANTIDADE FROM LIVROS_REGISTRADOS AS LR, LIVROS_ESTOQUE AS LE WHERE LR.ID = LE.ID_REG AND LR.NOME LIKE ?";
    private static final String SQL_LIVROS_BY_AUTOR = "SELECT LR.ID,LR.NOME,LR.AUTOR,LE.PRECO,LE.SECCAO,LE.QUANTIDADE FROM LIVROS_REGISTRADOS AS LR, LIVROS_ESTOQUE AS LE WHERE LR.ID = LE.ID_REG AND LR.AUTOR LIKE ?";
    private static final String SQL_GETID_BY_NOME_AUTOR = "SELECT ID FROM LIVROS_REGISTRADOS WHERE NOME = ? AND AUTOR = ?";
    private static final String SQL_GETQTD_BY_ID = "SELECT LE.QUANTIDADE FROM LIVROS_REGISTRADOS AS LR, LIVROS_ESTOQUE AS LE WHERE LR.ID = LE.ID_REG AND LR.ID = ?";
    private static final String SQL_GETROW_BY_ID =  "SELECT * FROM LIVROS_REGISTRADOS WHERE ID = ?";
    
    //insert
    private static final String SQL_INSERT_LIVROREG = "INSERT INTO LIVROS_REGISTRADOS(NOME,AUTOR) VALUES(?,?)";
    private static final String SQL_INSERT_LIVROEST = "INSERT INTO LIVROS_ESTOQUE(QUANTIDADE,SECCAO,ID_REG,PRECO) VALUES(?,?,?,?)";
    //delete
    private static final String SQL_DELETE_LIVROREG_BY_ID = "DELETE FROM LIVROS_REGISTRADOS WHERE ID = ?";
    private static final String SQL_INSERT_LIVROEST_BY_ID = "DELETE FROM LIVROS_ESTOQUE WHERE ID = ?";

    //UPDATE
    private static final String SQL_UPDATE_LIVROEST_BY_ID = "UPDATE LIVROS_ESTOQUE SET QUANTIDADE = ?,SECCAO = ?,PRECO = ? WHERE ID = ?";
    private static final String SQL_UPDATE_LIVROREG_BY_ID = "UPDATE LIVROS_REGISTRADOS SET NOME = ?,AUTOR = ? WHERE ID = ?";
    private static final String SQL_UPDATEQTD_LIVROEST_BY_ID = "UPDATE LIVROS_ESTOQUE SET QUANTIDADE = ? WHERE ID = ?";

    public List<Livro> getAllLivros(){
        return getLivroByAutor("");
    }
    
    public Boolean verifyIFExistIDLivro(Integer id){
        Boolean value = false;

        try (Connection cnn = getConnection()) {
            try (PreparedStatement ps = cnn.prepareStatement(SQL_GETROW_BY_ID)) {
                ps.setInt(1, id);

                try (ResultSet rs = ps.executeQuery()) {
                    value = rs.next();
                } catch (SQLException sql) {
                    System.out.println(sql.getMessage());
                    sql.printStackTrace(System.err);
                }

            } catch (SQLException sql) {
                System.out.println(sql.getMessage());
                sql.printStackTrace(System.err);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace(System.err);

        }

        return value;
    }
    
    public Boolean updateQtdById(Integer id, Integer quantidade) {
        Boolean value = false;

        try (Connection cnn = getConnection()) {
            try (PreparedStatement ps = cnn.prepareStatement(SQL_UPDATEQTD_LIVROEST_BY_ID)) {
                ps.setInt(1, quantidade);
                ps.setInt(2, id);

                ps.executeUpdate();
                value = true;

            } catch (SQLException sql) {
                System.out.println(sql.getMessage());
                sql.printStackTrace(System.err);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace(System.err);

        }

        return value;
    }

    public Integer selectQtdLivroByID(Integer id) {
        Integer value = 0;

        try (Connection cnn = getConnection()) {
            try (PreparedStatement ps = cnn.prepareStatement(SQL_GETQTD_BY_ID)) {
                ps.setInt(1, id);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        value = rs.getInt(1);
                    }
                } catch (SQLException sql) {
                    System.out.println(sql.getMessage());
                    sql.printStackTrace(System.err);
                }

            } catch (SQLException sql) {
                System.out.println(sql.getMessage());
                sql.printStackTrace(System.err);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace(System.err);

        }

        return value;
    }

    public Boolean updateLivroRegByID(Livro livro) {
        Boolean value = false;

        try (Connection cnn = getConnection()) {
            try (PreparedStatement ps = cnn.prepareStatement(SQL_UPDATE_LIVROREG_BY_ID)) {
                ps.setString(1, livro.getNome().toUpperCase());
                ps.setString(2, livro.getAutor().toUpperCase());
                ps.setInt(3, livro.getId());

                if (updateLivroEstByID(livro)) {
                    ps.executeUpdate();
                    value = true;
                }

            } catch (SQLException sql) {
                System.out.println(sql.getMessage());
                sql.printStackTrace(System.err);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace(System.err);

        }

        return value;
    }

    public Boolean updateLivroEstByID(Livro livro) {
        Boolean value = false;

        try (Connection cnn = getConnection()) {
            try (PreparedStatement ps = cnn.prepareStatement(SQL_UPDATE_LIVROEST_BY_ID)) {
                ps.setInt(1, livro.getQuantidade());
                ps.setString(2, livro.getSeccao().toUpperCase());
                ps.setFloat(3, livro.getPreco());
                ps.setInt(4, livro.getId());

                ps.executeUpdate();
                value = true;

            } catch (SQLException sql) {
                System.out.println(sql.getMessage());
                sql.printStackTrace(System.err);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace(System.err);

        }

        return value;
    }

    public Boolean deleteLivroRegByID(Integer id) {
        Boolean value = false;

        try (Connection cnn = getConnection()) {
            try (PreparedStatement ps = cnn.prepareStatement(SQL_DELETE_LIVROREG_BY_ID)) {
                ps.setInt(1, id);

                ps.executeUpdate();
                value = true;

            } catch (SQLException sql) {
                System.out.println(sql.getMessage());
                sql.printStackTrace(System.err);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace(System.err);

        }

        return value;
    }

    public Boolean deleteLivroEstByID(Integer id) {
        Boolean value = false;

        try (Connection cnn = getConnection()) {
            try (PreparedStatement ps = cnn.prepareStatement(SQL_INSERT_LIVROEST_BY_ID)) {
                ps.setInt(1, id);

                ps.executeUpdate();
                value = true;

            } catch (SQLException sql) {
                System.out.println(sql.getMessage());
                sql.printStackTrace(System.err);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace(System.err);

        }

        return value;
    }

    public Boolean insertLivroReg(String nome, String autor) {
        Boolean value = false;

        try (Connection cnn = getConnection()) {
            try (PreparedStatement ps = cnn.prepareStatement(SQL_INSERT_LIVROREG)) {
                ps.setString(1, nome);
                ps.setString(2, autor);

                ps.executeUpdate();
                value = true;

            } catch (SQLException sql) {
                System.out.println(sql.getMessage());
                sql.printStackTrace(System.err);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace(System.err);

        }

        return value;
    }

    public Boolean insertLivroEst(Integer quantidade, String seccao, Integer id_reg, Float preco) {
        Boolean value = false;

        try (Connection cnn = getConnection()) {
            try (PreparedStatement ps = cnn.prepareStatement(SQL_INSERT_LIVROEST)) {
                ps.setInt(1, quantidade);
                ps.setString(2, seccao);
                ps.setInt(3, id_reg);
                ps.setFloat(4, preco);

                ps.executeUpdate();
                value = true;

            } catch (SQLException sql) {
                System.out.println(sql.getMessage());
                sql.printStackTrace(System.err);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace(System.err);

        }

        return value;
    }

    public Boolean verifyIfExistLivro(String nome, String autor) {
        Boolean value = false;

        try (Connection cnn = getConnection()) {
            try (PreparedStatement ps = cnn.prepareStatement(SQL_GETID_BY_NOME_AUTOR)) {
                ps.setString(1, nome);
                ps.setString(2, autor);

                try (ResultSet rs = ps.executeQuery()) {
                    value = rs.next();
                } catch (SQLException sql) {
                    System.out.println(sql.getMessage());
                    sql.printStackTrace(System.err);
                }

            } catch (SQLException sql) {
                System.out.println(sql.getMessage());
                sql.printStackTrace(System.err);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace(System.err);

        }

        return value;
    }

    public Integer getIdByNomeAutor(String nome, String autor) {
        Integer value = 0;

        try (Connection cnn = getConnection()) {
            try (PreparedStatement ps = cnn.prepareStatement(SQL_GETID_BY_NOME_AUTOR)) {
                ps.setString(1, nome);
                ps.setString(2, autor);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        value = rs.getInt(1);
                    }
                } catch (SQLException sql) {
                    System.out.println(sql.getMessage());
                    sql.printStackTrace(System.err);
                }

            } catch (SQLException sql) {
                System.out.println(sql.getMessage());
                sql.printStackTrace(System.err);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace(System.err);

        }

        return value;
    }

    public List<Livro> getLivroByNome(String livro) {
        List<Livro> livros = new ArrayList<Livro>();
        try (Connection cnn = getConnection()) {
            try (PreparedStatement ps = cnn.prepareStatement(SQL_LIVROS_BY_NOME)) {
                ps.setString(1, (livro + "%"));
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        livros.add(new Livro(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getFloat(4), rs.getString(5), rs.getInt(6)));
                    }
                } catch (SQLException sql) {
                    System.out.println(sql.getMessage());
                    sql.printStackTrace(System.err);
                }
            } catch (SQLException sql) {
                System.out.println(sql.getMessage());
                sql.printStackTrace(System.err);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace(System.err);

        }
        return livros;
    }

    public List<Livro> getLivroByAutor(String autor) {
        List<Livro> livros = new ArrayList<Livro>();
        try (Connection cnn = getConnection()) {
            try (PreparedStatement ps = cnn.prepareStatement(SQL_LIVROS_BY_AUTOR)) {
                ps.setString(1, (autor + "%"));
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        livros.add(new Livro(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getFloat(4), rs.getString(5), rs.getInt(6)));
                    }
                } catch (SQLException sql) {
                    System.out.println(sql.getMessage());
                    sql.printStackTrace(System.err);
                }
            } catch (SQLException sql) {
                System.out.println(sql.getMessage());
                sql.printStackTrace(System.err);
            }

        } catch (Exception e) {
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
