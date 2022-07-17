package br.com.javafy.repository;

import br.com.javafy.config.DatabaseConnection;
import br.com.javafy.entity.Musica;
import br.com.javafy.entity.PlayList;
import br.com.javafy.exceptions.BancoDeDadosException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class PlayListMusicaRespository {
    @Autowired
    private DatabaseConnection dbconnection;

    public boolean create (Integer idPlaylist, String idMusica) throws SQLException {
        Connection connection = null;
        StringBuilder sql = new StringBuilder();

        try {
            connection = dbconnection.getConnection();


            sql.append("INSERT INTO LISTADEMUSICA(ID_PLAYLIST, ID_MUSICA)" +
                    "SELECT  ?, ? FROM DUAL " +
                    "WHERE NOT EXISTS (SELECT NULL  FROM table" +
                    "                    WHERE name = ?" +
                    "                  )");

            PreparedStatement stmt = connection.prepareStatement(sql.toString());


            stmt.setInt(1, idPlaylist);
            stmt.setString(2, idMusica);
            stmt.setString(2, idMusica);

            return stmt.executeUpdate() > 0;

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
