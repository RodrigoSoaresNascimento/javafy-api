package br.com.javafy.repository;

import br.com.javafy.config.DatabaseConnection;
import br.com.javafy.exceptions.BancoDeDadosException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

            sql.append("INSERT INTO EQUIPE_4.LISTADEMUSICAS (ID_PLAYLIST, ID_MUSICA) " +
                    "SELECT ? , ?  FROM DUAL " +
                    " WHERE NOT EXISTS (SELECT ID_PLAYLIST " +
                    "FROM EQUIPE_4.LISTADEMUSICAS " +
                    "WHERE ID_PLAYLIST = ? AND ID_MUSICA = ?)");

            PreparedStatement stmt = connection.prepareStatement(sql.toString());

            stmt.setInt(1, idPlaylist);
            stmt.setString(2, idMusica);
            stmt.setInt(3, idPlaylist);
            stmt.setString(4, idMusica);

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
