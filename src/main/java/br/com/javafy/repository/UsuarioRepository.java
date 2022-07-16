package br.com.javafy.repository;

import br.com.javafy.config.DatabaseConnection;
import br.com.javafy.entity.PlayList;
import br.com.javafy.entity.Usuario;
import br.com.javafy.enums.TiposdePlano;
import br.com.javafy.exceptions.BancoDeDadosException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@Slf4j
public class UsuarioRepository {

    @Autowired
    private DatabaseConnection dbconnection;

    public Integer getProximoId(Connection connection) throws BancoDeDadosException {
        try {
            String sql = "SELECT seq_id_usuario.nextval mysequence from DUAL";
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

    private void closeBD(Connection connection) {
        try {
            if(connection !=null) {
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
    }

    public Usuario getByID(Integer idUser) throws SQLException {
        Connection connection = null;
        StringBuilder sql = new StringBuilder();
        try {
            connection = dbconnection.getConnection();
            sql.append("SELECT * FROM EQUIPE_4.USUARIO WHERE ID_USER = ?");
            PreparedStatement stmt = connection.prepareStatement(sql.toString());
            stmt.setInt(1, idUser);
            ResultSet resultSet = stmt.executeQuery();
            Usuario usuario = new Usuario();

            if (!resultSet.next() ) {
                // lançar exceção aqui
            }

            while (resultSet.next()) {
                resultSetToUsuario(usuario, resultSet);
            }
            return usuario;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
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






//    public Usuario create (Usuario ouvinte){
//        ouvinte.setIdUsuario(COUNTER.incrementAndGet());
//        usuarios.add(ouvinte);
//        return ouvinte;
//    }

//    public UsuarioRepository () {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        usuarios.add(new Usuario(COUNTER.incrementAndGet(), "Rodrigo", LocalDate.parse("24/03/1997", formatter), "M", 1, "rodrigo@gmail.com",1));
//    }
}
