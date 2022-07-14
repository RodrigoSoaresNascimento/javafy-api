package br.com.javafy.repository;

import br.com.javafy.entity.Ouvinte;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class OuvinteRepository {

    private List<Ouvinte> ouvintes = new ArrayList<>();

    private AtomicInteger COUNTER = new AtomicInteger();

    public OuvinteRepository () {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        ouvintes.add(new Ouvinte(COUNTER.incrementAndGet(), "Rodrigo", LocalDate.parse("24/03/1997", formatter), "M", 1, "rodrigo@gmail.com",1));
    }

    public List<Ouvinte> list() {
        return ouvintes;
    }

    public Ouvinte create (Ouvinte ouvinte){
        ouvinte.setIdUser(COUNTER.incrementAndGet());
        ouvintes.add(ouvinte);
        return ouvinte;
    }


}
