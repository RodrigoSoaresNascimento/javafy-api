package br.com.javafy.repository;

import br.com.javafy.config.DatabaseConnection;
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

    @Autowired
    private DatabaseConnection dbConnection;
    public List<Usuario> getAllSeguidores(Integer idUsuario) throws SQLException {

        List<Usuario> usuarios = new ArrayList<>();

        Connection connection = dbConnection.getConnection();
        try {
            String sql = "SELECT u.ID_USER, u.NOME, u.DATA_NASCIMENTO , u.GENERO, u.PREMIUM  " +
                    "FROM SEGUIDORES s " +
                    "JOIN USUARIO u ON u.ID_USER = s.ID_USER " +
                    "WHERE s.ID_USER_SEGUINDO  = " + idUsuario;

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            // Vamos só listar os usuários - Então podemos criar um Ouvinte sem ID
            while (resultSet.next()){
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(resultSet.getInt("ID_USER"));
                usuario.setNome(resultSet.getString("NOME"));
                usuario.setGenero(resultSet.getString("GENERO"));
                usuario.setPlano((TiposdePlano) resultSet.getObject("PREMIUM"));
                usuarios.add(usuario);
            }

            return usuarios;

        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Usuario> getAllSeguindo(Integer idUsuario) throws SQLException {
        Connection connection = dbConnection.getConnection();

        try {
            String sql = "SELECT u.ID_USER, u.NOME, u.DATA_NASCIMENTO, u.GENERO, u.PREMIUM " +
                    " FROM VEM_SER.SEGUIDORES s " +
                    "JOIN USUARIO u ON u.ID_USER = s.ID_USER_SEGUINDO " +
                    "WHERE s.ID_USER = " + idUsuario;
            List<Usuario> usuarios = new ArrayList<>();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            // Vamos só listar os usuários - Então podemos criar um Usuario sem ID
            while (resultSet.next()){
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(resultSet.getInt("ID_USER"));
                usuario.setNome(resultSet.getString("NOME"));
                usuario.setGenero(resultSet.getString("GENERO"));
                usuario.setPlano((TiposdePlano) resultSet.getObject("PREMIUM"));
                usuarios.add(usuario);
            }

            return usuarios;

        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public boolean seguirUsuario(Integer idUsuarioParaSeguir, Usuario usuario) throws SQLException {
        Connection connection = dbConnection.getConnection();
        List<Usuario> usuarios = new ArrayList<>();

        try {

            String sql = "INSERT INTO SEGUIDORES (ID_USER, ID_USER_SEGUINDO) VALUES (?,?)";
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, usuario.getIdUsuario());
            stmt.setInt(2, idUsuarioParaSeguir);

            int res = stmt.executeUpdate();
            return res > 0;

        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean deixarDeSeguirUsuario(Integer idUsuarioSeguindo, Usuario usuario)
            throws SQLException {
        Connection connection = dbConnection.getConnection();
        List<Usuario> usuarios = new ArrayList<>();

        try {

            String sql = "DELETE FROM SEGUIDORES s WHERE s.ID_USER = ? AND s.ID_USER_SEGUINDO = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, usuario.getIdUsuario());
            stmt.setInt(2, idUsuarioSeguindo);

            int res = stmt.executeUpdate();
            return res > 0;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}