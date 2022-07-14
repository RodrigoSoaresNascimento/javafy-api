package br.com.javafy.controller;

import br.com.javafy.dto.OuvinteCreateDTO;
import br.com.javafy.dto.OuvinteDTO;
import br.com.javafy.exceptions.PessoaNaoCadastradaException;
import br.com.javafy.service.OuvinteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/ouvinte")
@Validated
public class OuvinteController {

    @Autowired
    private OuvinteService ouvinteService;

    @PostMapping
    public ResponseEntity<OuvinteDTO> create(@Valid @RequestBody OuvinteCreateDTO ouvinte) {
        return ResponseEntity.ok(ouvinteService.create(ouvinte));

    }

    @GetMapping
    public  ResponseEntity<List<OuvinteDTO>> list () {
        return ResponseEntity.ok(ouvinteService.list());
    }

    @PutMapping("/{idUser}")
    public ResponseEntity<OuvinteDTO> update (@PathVariable("idUser") Integer id
                                                , @Valid @RequestBody OuvinteCreateDTO ouvinte) throws PessoaNaoCadastradaException {
        return ResponseEntity.ok(ouvinteService.update(ouvinte,id));
    }

    @DeleteMapping("/{idUser}")
    public void delete (@PathVariable ("idUser") Integer id) throws PessoaNaoCadastradaException {
        ouvinteService.delete(id);
    }

    @GetMapping("/byname")
    public  ResponseEntity<List<OuvinteDTO>> listByName (@RequestParam("nome=") String nome){
        return ResponseEntity.ok(ouvinteService.listByName(nome));
    }
}