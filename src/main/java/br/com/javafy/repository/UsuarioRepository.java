package br.com.javafy.repository;

import br.com.javafy.config.DatabaseConnection;
import br.com.javafy.entity.Usuario;
import br.com.javafy.enums.TiposdePlano;
import br.com.javafy.exceptions.BancoDeDadosException;
import br.com.javafy.exceptions.PessoaNaoCadastradaException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class UsuarioRepository {

    @Autowired
    private DatabaseConnection dbconnection;

    public Integer getProximoId(Connection connection) throws BancoDeDadosException {
        try {
            String sql = "SELECT seq_id_user.nextval mysequence from DUAL";
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery(sql);

            if (res.next()) {
                return res.getInt("mysequence");
            }
            return null;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        }
    }

    private void closeBD(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void resultSetToUsuario(Usuario usuario, ResultSet resultSet) throws SQLException {
        usuario.setIdUsuario(resultSet.getInt("ID_USER"));
        usuario.setNome(resultSet.getString("NOME"));
        usuario.setDataNascimento(resultSet.getDate("DATA_NASCIMENTO").toLocalDate());
        usuario.setGenero(resultSet.getString("GENERO"));
        if (resultSet.getInt("PREMIUM") == 1) {
            usuario.setPlano(TiposdePlano.PREMIUM);
        } else {
            usuario.setPlano(TiposdePlano.FREE);
        }
        usuario.setEmail(resultSet.getString("EMAIL"));
        System.out.println(usuario);
    }


    public Usuario findByID(Integer idUser) throws SQLException, PessoaNaoCadastradaException {
        Connection connection = null;
        try {
            connection = dbconnection.getConnection();

            String sql = "SELECT * FROM EQUIPE_4.USUARIO WHERE ID_USER = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idUser);
            ResultSet resultSet = stmt.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                throw new PessoaNaoCadastradaException("O ID da pessoa informada não existe.");
            }

            Usuario usuario = new Usuario();
            while (resultSet.next()) {
                resultSetToUsuario(usuario, resultSet);
            }
            return usuario;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } catch (PessoaNaoCadastradaException e) {
            throw new PessoaNaoCadastradaException(e.getMessage());
        } finally {
            closeBD(connection);
        }
    }

    public List<Usuario> list() throws SQLException {
        Connection connection = dbconnection.getConnection();

        String sql = "SELECT * FROM EQUIPE_4.USUARIO";
        List<Usuario> usuarios = new ArrayList<>();

        try {

            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                Usuario usuario = new Usuario();
                resultSetToUsuario(usuario, resultSet);
                usuarios.add(usuario);
            }
            return usuarios;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            closeBD(connection);
        }

    }

    public Usuario create(Usuario usuario) throws SQLException {
        Connection connection = null;
        StringBuilder sql = new StringBuilder();

        try {
            connection = dbconnection.getConnection();

            sql.append("INSERT INTO USUARIO(ID_USER, NOME, DATA_NASCIMENTO, GENERO, PREMIUM, EMAIL)" +
                    "VALUES(?, ?, ?, ?, ?, ?)");

            PreparedStatement stmt = connection.prepareStatement(sql.toString());

            Integer promixoId = getProximoId(connection);

            stmt.setInt(1, promixoId);
            stmt.setString(2, usuario.getNome());
            stmt.setDate(3, Date.valueOf(usuario.getDataNascimento()));
            stmt.setString(4, usuario.getGenero());
            if (usuario.getPlano().toString().equals("PREMIUM")) {
                stmt.setInt(5, 1);
            } else {
                stmt.setInt(5, 0);
            }
            stmt.setString(6, usuario.getEmail());


            ResultSet resultSet = stmt.executeQuery();

            return usuario;

        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            closeBD(connection);
        }
    }

    public void delete(Integer idUsuario) throws SQLException {
        Connection connection = dbconnection.getConnection();
        StringBuilder sql = new StringBuilder();
        try {
            sql.append("DELETE FROM USUARIO WHERE ID_USER = ?");

            PreparedStatement stmt = connection.prepareStatement(sql.toString());
            stmt.setInt(1, idUsuario);
            ResultSet resultSet = stmt.executeQuery();
            System.out.println("Usuario id = " + idUsuario + " foi deletado");
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            closeBD(connection);
        }
    }

    public boolean update(Integer idUsuario, Usuario usuario) throws SQLException {
        Connection connection = dbconnection.getConnection();
        StringBuilder sql = new StringBuilder();

        try {
            sql.append("UPDATE USUARIO " +
                    "SET NOME = ?, DATA_NASCIMENTO = ?, GENERO = ?, PREMIUM = ?, EMAIL = ?" +
                    "WHERE ID_USER = ?");

            PreparedStatement stmt = connection.prepareStatement(sql.toString());

            stmt.setString(1, usuario.getNome());
            stmt.setDate(2, Date.valueOf(usuario.getDataNascimento()));
            stmt.setString(3, usuario.getGenero());
            //TODO - corrigir enum <- string
            if (usuario.getPlano().toString().equals("PREMIUM")) {
                stmt.setInt(4, 1);
            } else {
                stmt.setInt(4, 0);
            }
            stmt.setString(5, usuario.getEmail());
            stmt.setInt(6,idUsuario);


            int res = stmt.executeUpdate();
            return res > 0;

        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            closeBD(connection);
        }
    }
}
