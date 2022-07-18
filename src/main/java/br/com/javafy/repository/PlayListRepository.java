package br.com.javafy.repository;

import br.com.javafy.config.DatabaseConnection;
import br.com.javafy.entity.PlayList;
import br.com.javafy.exceptions.BancoDeDadosException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

            if(res.next()) {
                return res.getInt("mysequence");
            }
            return null;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        }
    }

    private void closeBd(Connection connection) {
        try {
            if(connection !=null) {
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

    public PlayList create (PlayList playList) throws SQLException {
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

            ResultSet resultSet = stmt.executeQuery();

            return playList;

        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            closeBd(connection);
        }
    }

    public boolean delete (Integer idPlaylist) throws SQLException {
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

    public boolean update (Integer idPlaylist, PlayList playlist) throws SQLException {
        Connection connection = null;
        try {
            connection = dbconnection.getConnection();
            String sql = "UPDATE EQUIPE_4.PLAYLIST " +
                    "SET NAME = ? " +
                    "WHERE ID_PLAYLIST = ?";

            PreparedStatement  stmt = connection.prepareStatement(sql);

            stmt.setString(1, playlist.getName());
            stmt.setInt(2, idPlaylist);

            return  stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            closeBd(connection);
        }
    }

    public List<PlayList> list(Integer idUser) throws SQLException {
        List<PlayList> playlists = new ArrayList<>();
        Connection connection = null;
        try {
            connection = dbconnection.getConnection();
            String sql = "SELECT * FROM PLAYLIST p WHERE p.ID_OUVINTE = ?";
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);

//            while (resultSet.next()) {
//                PlayList playlist = new PlayList();
//                playlist.setArtista((Usuario) user);
//                playlist.setIdPlaylist(resultSet.getInt("id_playlist"));
//                playlist.setNome(resultSet.getString("nome"));
//                playlists.add(playlist);
//            }
            return playlists;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            closeBd(connection);
        }

    }


}
