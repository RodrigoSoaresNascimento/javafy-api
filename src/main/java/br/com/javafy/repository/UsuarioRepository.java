package br.com.javafy.repository;

import br.com.javafy.entity.PlayList;
import br.com.javafy.entity.Usuario;
import br.com.javafy.exceptions.BancoDeDadosException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class UsuarioRepository {
    @Autowired
    private Connection connection;

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

//    public List<Usuario> list() {
//        return usuarios;
//    }
    public List<Usuario> listar() throws BancoDeDadosException {
        String sql = "SELECT * FROM USUARIO";

        List<Usuario> usuarios = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);

            while (resultSet.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(usuario.getIdUsuario());
                usuario.setNome(usuario.getNome());
                usuario.setEmail(usuario.getEmail());
                usuario.setPlano(usuario.getPlano());
                usuario.setGenero(usuario.getGenero());
                usuario.setPlayLists(usuario.getPlayLists());

                usuarios.add(usuario);
            }
            return usuarios;
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
