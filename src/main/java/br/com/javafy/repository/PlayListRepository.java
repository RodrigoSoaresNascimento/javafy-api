package br.com.javafy.repository;

import br.com.javafy.config.DatabaseConnection;
import br.com.javafy.entity.Musica;
import br.com.javafy.entity.PlayList;
import br.com.javafy.exceptions.BancoDeDadosException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PlayListRepository {

    @Autowired
    private DatabaseConnection dbconnection;

    public Integer getProximoId(Connection connection) throws BancoDeDadosException {

        try {
            Connection con = connection.createStatement().getConnection();
            String sql = "SELECT seq_id_playlist.nextval mysequence from DUAL";
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

    private void closeBd(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public PlayList getPlaylistById(Integer idPlaylist) throws SQLException {

        PlayList playlist = new PlayList();
        Connection connection = null;

        try {
            connection = dbconnection.getConnection();

            String sql = "SELECT * FROM EQUIPE_4.PLAYLIST P " +
                    "WHERE P.ID_PLAYLIST = ?";

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idPlaylist);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                playlist.setIdPlaylist(resultSet.getInt("id_playlist"));
                playlist.setName(resultSet.getString("nome"));
            }
            return playlist;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            closeBd(connection);
        }
    }

    public PlayList create(PlayList playList) throws SQLException {
        Connection connection = null;
        StringBuilder sql = new StringBuilder();

        try {
            connection = dbconnection.getConnection();

            sql.append("INSERT INTO PLAYLIST(ID_PLAYLIST, ID_USER, NOME)" +
                    "VALUES(?, ?, ?)");

            PreparedStatement stmt = connection.prepareStatement(sql.toString());

            Integer promixoId = getProximoId(connection);

            stmt.setInt(1, promixoId);
            stmt.setInt(2, playList.getUsuario().getIdUsuario());
            stmt.setString(3, playList.getName());

            playList.setIdPlaylist(promixoId);

            stmt.executeUpdate();
            if (playList.getMusicas() != null) {
                for (Musica musica : playList.getMusicas()) {
                    String sqlMusica = "INSERT INTO LISTADEMUSICAS(ID_PLAYLIST, ID_MUSICA) " +
                            "VALUES(?, ?)";
                    PreparedStatement stmtMusica = connection.prepareStatement(sqlMusica);
                    stmtMusica.setInt(1, promixoId);
                    stmtMusica.setString(2, musica.getId());

                    stmtMusica.executeUpdate();
                }
            }

            return playList;

        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            closeBd(connection);
        }
    }

    public boolean delete(Integer idPlaylist) throws SQLException {
        Connection connection = null;
        try {
            connection = dbconnection.getConnection();
            String sql = "DELETE FROM EQUIPE_4.PLAYLIST p WHERE p.ID_PLAYLIST = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idPlaylist);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            closeBd(connection);
        }
    }

    public boolean update(Integer idPlaylist, PlayList playlist) throws SQLException {
        Connection connection = null;
        try {
            connection = dbconnection.getConnection();
            String sql = "UPDATE EQUIPE_4.PLAYLIST " +
                    "SET NOME = ? " +
                    "WHERE ID_PLAYLIST = ?";

            PreparedStatement stmt = connection.prepareStatement(sql);
//            Musica musica = playlist.getMusicas().stream().findFirst();

            stmt.setString(1, playlist.getName());
//            stmt.setString(2, musica);
            stmt.setInt(2, idPlaylist);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            closeBd(connection);
        }
    }


}
