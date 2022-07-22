package br.com.javafy.repository;

import br.com.javafy.entity.Usuario;
import br.com.javafy.enums.TiposdePlano;
import br.com.javafy.exceptions.BancoDeDadosException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SeguidoresRepository {

//    @Autowired
//    private DatabaseConnection dbConnection;
//    public List<Usuario> getAllSeguidores(Integer idUsuario) throws SQLException {
//
//        List<Usuario> usuarios = new ArrayList<>();
//        StringBuilder sql = new StringBuilder();
//        Connection connection = dbConnection.getConnection();
//        try {
//            sql.append("SELECT * " +
//                    "FROM SEGUIDORES s " +
//                    "JOIN USUARIO u ON u.ID_USER = s.ID_USER " +
//                    "WHERE s.ID_USER_SEGUINDO  = " + idUsuario) ;
//
//            PreparedStatement statement = connection.prepareStatement(sql.toString());
//            ResultSet resultSet = statement.executeQuery(sql.toString());
//
//            // Vamos só listar os usuários - Então podemos criar um Ouvinte sem ID
//            while (resultSet.next()){
//                Usuario usuario = new Usuario();
//                usuario.setIdUsuario(resultSet.getInt("ID_USER"));
//                usuario.setNome(resultSet.getString("NOME"));
//                usuario.setGenero(resultSet.getString("GENERO"));
//                usuario.setDataNascimento(resultSet.getDate("DATA_NASCIMENTO").toLocalDate());
//                usuario.setEmail(resultSet.getString("EMAIL"));
//                if (resultSet.getInt("PREMIUM") == 1) {
//                    usuario.setPlano(TiposdePlano.PREMIUM);
//                } else {
//                    usuario.setPlano(TiposdePlano.FREE);
//                }
//                usuarios.add(usuario);
//            }
//
//            return usuarios;
//
//        } catch (SQLException e) {
//            throw new BancoDeDadosException(e.getCause());
//        } finally {
//            try {
//                if (connection != null) {
//                    connection.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public List<Usuario> getAllSeguindo(Integer idUsuario) throws SQLException {
//        Connection connection = dbConnection.getConnection();
//        StringBuilder sql = new StringBuilder();
//        try {
//            sql.append("SELECT * " +
//                    " FROM SEGUIDORES s " +
//                    "JOIN USUARIO u ON u.ID_USER = s.ID_USER_SEGUINDO " +
//                    "WHERE s.ID_USER = " + idUsuario);
//            List<Usuario> usuarios = new ArrayList<>();
//            PreparedStatement statement = connection.prepareStatement(sql.toString());
//            ResultSet resultSet = statement.executeQuery(sql.toString());
//
//            // Vamos só listar os usuários - Então podemos criar um Usuario sem ID
//            while (resultSet.next()){
//                Usuario usuario = new Usuario();
//                usuario.setIdUsuario(resultSet.getInt("ID_USER"));
//                usuario.setNome(resultSet.getString("NOME"));
//                usuario.setGenero(resultSet.getString("GENERO"));
//                usuario.setDataNascimento(resultSet.getDate("DATA_NASCIMENTO").toLocalDate());
//                usuario.setEmail(resultSet.getString("EMAIL"));
//                if (resultSet.getInt("PREMIUM") == 1) {
//                    usuario.setPlano(TiposdePlano.PREMIUM);
//                } else {
//                    usuario.setPlano(TiposdePlano.FREE);
//                }
//                usuarios.add(usuario);
//            }
//
//            return usuarios;
//
//        } catch (SQLException e) {
//            throw new BancoDeDadosException(e.getCause());
//        } finally {
//            try {
//                if (connection != null) {
//                    connection.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
//
//    public boolean seguirUsuario(Integer meuId ,Integer idUsuarioParaSeguir) throws SQLException {
//        Connection connection = dbConnection.getConnection();
//
//        try {
//
//            String sql = "INSERT INTO SEGUIDORES s (ID_USER, ID_USER_SEGUINDO) VALUES "
//                    +"(?,?)";
//            PreparedStatement stmt = connection.prepareStatement(sql);
//
//            stmt.setInt(1, meuId);
//            stmt.setInt(2, idUsuarioParaSeguir);
//            int res = stmt.executeUpdate();
//            return res > 0;
//
//        } catch (SQLException e) {
//            throw new BancoDeDadosException(e.getCause());
//        } finally {
//            try {
//                if (connection != null) {
//                    connection.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public boolean deixarDeSeguirUsuario(Integer meuId, Integer idUsuarioSeguindo)
//            throws SQLException {
//        Connection connection = dbConnection.getConnection();
//        try {
//
//            String sql = "DELETE FROM SEGUIDORES s WHERE s.ID_USER = ? AND s.ID_USER_SEGUINDO = ?";
//            PreparedStatement stmt = connection.prepareStatement(sql);
//
//            stmt.setInt(1, meuId);
//            stmt.setInt(2, idUsuarioSeguindo);
//
//            int res = stmt.executeUpdate();
//            return res > 0;
//        } catch (SQLException e) {
//            throw new BancoDeDadosException(e.getCause());
//        } finally {
//            try {
//                if (connection != null) {
//                    connection.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }

}
