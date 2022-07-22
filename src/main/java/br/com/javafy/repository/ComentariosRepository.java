package br.com.javafy.repository;

import br.com.javafy.entity.Comentario;
import br.com.javafy.exceptions.BancoDeDadosException;
import br.com.javafy.exceptions.PessoaNaoCadastradaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface ComentariosRepository extends JpaRepository<Comentario, Integer> {

//    @Autowired
//    private DatabaseConnection dbconnection;
//
//    public Integer getProximoId(Connection connection) throws BancoDeDadosException {
//        try {
//            String sql = "SELECT seq_id_comentario.nextval mysequence from DUAL";
//            Statement stmt = connection.createStatement();
//            ResultSet res = stmt.executeQuery(sql);
//
//            if (res.next()) {
//                return res.getInt("mysequence");
//            }
//            return null;
//        } catch (SQLException e) {
//            throw new BancoDeDadosException(e.getCause());
//        }
//    }
//
//    private void closeBD(Connection connection) {
//        try {
//            if (connection != null) {
//                connection.close();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void resultSetToComentario(Comentario comentario, ResultSet resultSet) throws SQLException {
//        comentario.setIdUser(resultSet.getInt("ID_USER"));
//        comentario.setComentario(resultSet.getString("COMENTARIO"));
//        comentario.setIdComentario(resultSet.getInt("ID_COMENTARIO"));
//        comentario.setIdPlaylist(resultSet.getInt("ID_PLAYLIST"));
//        System.out.println(comentario);
//    }
//
//
//    public Comentario findByID(Integer idComentario) throws SQLException, PessoaNaoCadastradaException {
//        Connection connection = null;
//        try {
//            connection = dbconnection.getConnection();
//
//            String sql = "SELECT * FROM COMENTARIOS WHERE ID_COMENTARIO = ?";
//            PreparedStatement stmt = connection.prepareStatement(sql);
//            stmt.setInt(1, idComentario);
//            ResultSet resultSet = stmt.executeQuery();
//
//            if (!resultSet.isBeforeFirst()) {
//                throw new PessoaNaoCadastradaException("O ID do comentario informado não existe.");
//            }
//
//            Comentario comentario = new Comentario();
//            while (resultSet.next()) {
//                resultSetToComentario(comentario, resultSet);
//            }
//            return comentario;
//        } catch (SQLException e) {
//            throw new BancoDeDadosException(e.getCause());
//        } catch (PessoaNaoCadastradaException e) {
//            throw new PessoaNaoCadastradaException(e.getMessage());
//        } finally {
//            closeBD(connection);
//        }
//    }
//
//    public List<Comentario> list() throws SQLException {
//        Connection connection = dbconnection.getConnection();
//
//        String sql = "SELECT * FROM COMENTARIOS";
//        List<Comentario> comentarios = new ArrayList<>();
//
//        try {
//
//            PreparedStatement stmt = connection.prepareStatement(sql);
//            ResultSet resultSet = stmt.executeQuery();
//
//            while (resultSet.next()) {
//                Comentario comentario = new Comentario();
//                resultSetToComentario(comentario, resultSet);
//                comentarios.add(comentario);
//            }
//            return comentarios;
//        } catch (SQLException e) {
//            throw new BancoDeDadosException(e.getCause());
//        } finally {
//            closeBD(connection);
//        }
//
//    }
//
//    public Comentario create(Integer idUser, Integer idPlaylist, Comentario comentario) throws SQLException {
//        Connection connection = null;
//        StringBuilder sql = new StringBuilder();
//
//        try {
//            connection = dbconnection.getConnection();
//
//            sql.append("INSERT INTO COMENTARIOS(ID_USER, COMENTARIO, ID_COMENTARIO, ID_PLAYLIST)" +
//                    "VALUES(?, ?, ?, ?)");
//
//            PreparedStatement stmt = connection.prepareStatement(sql.toString());
//
//            Integer promixoId = getProximoId(connection);
//
//            stmt.setInt(1, idUser);
//            stmt.setString(2, comentario.getComentario());
//            stmt.setInt(3, promixoId);
//            stmt.setInt(4, idPlaylist);
//
//            ResultSet resultSet = stmt.executeQuery();
//            return comentario;
//
//        } catch (SQLException e) {
//            throw new BancoDeDadosException(e.getCause());
//        } finally {
//            closeBD(connection);
//        }
//    }
//
//    public void delete(Integer idComentario) throws SQLException {
//        Connection connection = dbconnection.getConnection();
//        StringBuilder sql = new StringBuilder();
//        try {
//            sql.append("DELETE FROM COMENTARIOS WHERE ID_COMENTARIO = ?");
//            PreparedStatement stmt = connection.prepareStatement(sql.toString());
//            stmt.setInt(1, idComentario);
//            ResultSet resultSet = stmt.executeQuery();
//        } catch (SQLException e) {
//            throw new BancoDeDadosException(e.getCause());
//        } finally {
//            closeBD(connection);
//        }
//    }
//
//    public boolean update(Integer idComentario, Comentario comentario) throws SQLException {
//        Connection connection = dbconnection.getConnection();
//        StringBuilder sql = new StringBuilder();
//
//        try {
//            sql.append("UPDATE COMENTARIOS " +
//                    "SET COMENTARIO = ?" +
//                    "WHERE ID_COMENTARIO = ?");
//
//            PreparedStatement stmt = connection.prepareStatement(sql.toString());
//
//            stmt.setString(1, comentario.getComentario());
//            stmt.setInt(2, idComentario);
//
//            int res = stmt.executeUpdate();
//            System.out.println("res = "+res);
//            return res > 0;
//
//        } catch (SQLException e) {
//            throw new BancoDeDadosException(e.getCause());
//        } finally {
//            closeBD(connection);
//        }
//    }

}
