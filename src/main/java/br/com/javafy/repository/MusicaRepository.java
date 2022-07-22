package br.com.javafy.repository;

import br.com.javafy.entity.MusicaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MusicaRepository extends JpaRepository<MusicaEntity, String>  {

//    @Autowired
//    private DatabaseConnection dbconnection;
//
//    private void closeBD(Connection connection) {
//        try {
//            if(connection !=null) {
//                connection.close();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public boolean create (Integer idPlaylist, String idMusica) throws SQLException {
//        Connection connection = null;
//        StringBuilder sql = new StringBuilder();
//
//        try {
//            connection = dbconnection.getConnection();
//
//            sql.append("INSERT INTO EQUIPE_4.LISTADEMUSICAS (ID_PLAYLIST, ID_MUSICA) " +
//                    "SELECT ? , ?  FROM DUAL " +
//                    " WHERE NOT EXISTS (SELECT ID_PLAYLIST " +
//                    "FROM EQUIPE_4.LISTADEMUSICAS " +
//                    "WHERE ID_PLAYLIST = ? AND ID_MUSICA = ?)");
//
//            PreparedStatement stmt = connection.prepareStatement(sql.toString());
//
//            stmt.setInt(1, idPlaylist);
//            stmt.setString(2, idMusica);
//            stmt.setInt(3, idPlaylist);
//            stmt.setString(4, idMusica);
//
//            return stmt.executeUpdate() > 0;
//
//        } catch (SQLException e) {
//            throw new BancoDeDadosException(e.getCause());
//        } finally {
//            closeBD(connection);
//        }
//    }
//
//    public List<String> getIdMusica(Integer idPlaylist) throws BancoDeDadosException {
//        Connection connection = null;
//        StringBuilder sql = new StringBuilder();
//
//        List<String> listMusicas = new ArrayList<>();
//        try {
//            connection = dbconnection.getConnection();
//
//            sql.append("SELECT ID_MUSICA FROM EQUIPE_4.LISTADEMUSICAS LM " +
//                    "WHERE LM.ID_PLAYLIST = ?");
//
//            PreparedStatement stmt = connection.prepareStatement(sql.toString());
//
//            stmt.setInt(1, idPlaylist);
//            ResultSet resultSet = stmt.executeQuery();
//
//            while(resultSet.next()){
//                listMusicas.add(resultSet.getString("ID_MUSICA"));
//            }
//
//
//            return listMusicas;
//
//        } catch (SQLException e) {
//            throw new BancoDeDadosException(e.getCause());
//        } finally {
//            closeBD(connection);
//        }
//    }
//
//    public Musica getMusicaInPlaylist(Integer idPlaylist, Integer idMusica) throws BancoDeDadosException, PlayListException {
//        Connection connection = null;
//        StringBuilder sql = new StringBuilder();
//
//        Musica musica = new Musica();
//        try {
//            connection = dbconnection.getConnection();
//
//            sql.append("SELECT ID_MUSICA FROM EQUIPE_4.LISTADEMUSICAS LM " +
//                    "WHERE LM.ID_PLAYLIST = ? AND LM.ID_MUSICA = ?");
//
//            PreparedStatement stmt = connection.prepareStatement(sql.toString());
//
//            stmt.setInt(1, idPlaylist);
//            stmt.setInt(1, idMusica);
//            ResultSet resultSet = stmt.executeQuery();
//
//            if (!resultSet.isBeforeFirst()) {
//                throw new PlayListException("O ID da pessoa informada não existe.");
//            }
//
//            musica.setId(resultSet.getString("ID_MUSICA"));
//            return musica;
//        } catch (SQLException e) {
//            throw new BancoDeDadosException(e.getCause());
//        } catch (PlayListException e) {
//            throw new PlayListException("Musica não está na playlist");
//        } finally {
//            closeBD(connection);
//        }
//    }
}
