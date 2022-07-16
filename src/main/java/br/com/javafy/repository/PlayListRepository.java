package br.com.javafy.repository;

import br.com.javafy.config.DatabaseConnection;
import br.com.javafy.entity.Ouvinte;
import br.com.javafy.entity.PlayList;
import br.com.javafy.entity.Usuario;
import br.com.javafy.exceptions.BancoDeDadosException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PlayListRepository {

    @Autowired
    private DatabaseConnection dbConnection;

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

    public PlayList create (PlayList playList) throws SQLException {
        Connection connection = dbConnection.getConnection();
        try {
            String sql = "INSERT INTO PLAYLIST(ID_PLAYLIST, ID_OUVINTE, NOME)" +
                    "VALUES(?, ?, ?)";


            Integer promixoId = getProximoId(connection);
            playList.setIdPlaylist(promixoId);

            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, playList.getIdPlaylist());
            stmt.setInt(2, playList.getProprietario().getIdOuvinte());
            stmt.setString(3, playList.getNome());
            int res = stmt.executeUpdate();
            return playList;

        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if(connection!=null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean remove (Integer id) throws SQLException {
        List<PlayList> playlists = new ArrayList<>();
        Connection connection = dbConnection.getConnection();
        try {

            String sql = "DELETE FROM VEM_SER.PLAYLIST p WHERE p.ID_PLAYLIST = " + id;
            PreparedStatement stmt = connection.prepareStatement(sql);
            int result = stmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if(connection!=null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean update (Integer idPlaylist, PlayList playlist) throws SQLException {
        Connection connection = dbConnection.getConnection();
        try {
            String sql = "UPDATE VEM_SER.PLAYLIST " +
                    "SET NOME = ? " +
                    "WHERE ID_PLAYLIST = ?";


            PreparedStatement  stmt = connection.prepareStatement(sql);

            stmt.setString(1, playlist.getNome());
            stmt.setInt(2, idPlaylist);

            int res = stmt.executeUpdate();
            return  res > 0;

        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if(connection!=null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<PlayList> list(Usuario user) throws SQLException {
        Connection connection = dbConnection.getConnection();
        List<PlayList> playlists = new ArrayList<>();
        try {
            String sql = "SELECT * FROM PLAYLIST p WHERE p.ID_OUVINTE = " + user.getIdUser();
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);

            while (resultSet.next()) {
                PlayList playlist = new PlayList();
                playlist.setProprietario((Ouvinte) user);
                playlist.setIdPlaylist(resultSet.getInt("id_playlist"));
                playlist.setNome(resultSet.getString("nome"));
                playlists.add(playlist);
            }
            return playlists;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if(connection!=null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public PlayList getPlaylistById(Integer id, Usuario user) throws SQLException {
        Connection connection = dbConnection.getConnection();
        PlayList playlist = null;
        try {
            String sql = "SELECT * FROM VEM_SER.PLAYLIST P " +
                    "WHERE P.ID_PLAYLIST = " + id + " AND P.ID_OUVINTE = " + user.getIdUser();


            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);

            while (resultSet.next()) {
                playlist = new PlayList();
                playlist.setIdPlaylist(resultSet.getInt("id_playlist"));
                playlist.setProprietario((Ouvinte) user);
                playlist.setNome(resultSet.getString("nome"));
            }
            return playlist;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if(connection!=null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
