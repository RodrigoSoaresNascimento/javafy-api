package br.com.javafy.service;

import br.com.javafy.dto.OuvinteCreateDTO;
import br.com.javafy.dto.OuvinteDTO;
import br.com.javafy.entity.Ouvinte;
import br.com.javafy.exceptions.PessoaNaoCadastradaException;
import br.com.javafy.repository.OuvinteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OuvinteService {

    @Autowired
    OuvinteRepository ouvinteRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public Ouvinte converterOuvinteDTO (OuvinteCreateDTO ouvinteCreateDTO){
        return objectMapper.convertValue(ouvinteCreateDTO, Ouvinte.class);
    }

    public OuvinteDTO converterOuvinte (Ouvinte ouvinte){
        return objectMapper.convertValue(ouvinte, OuvinteDTO.class);
    }

    public OuvinteDTO create (OuvinteCreateDTO ouvinte){
        Ouvinte ouvinteEntity = converterOuvinteDTO(ouvinte);
        ouvinteRepository.create(ouvinteEntity);
        return converterOuvinte(ouvinteEntity);
    }

    public List<OuvinteDTO> list () {
        return ouvinteRepository.list()
                .stream()
                .map(this::converterOuvinte)
                .collect(Collectors.toList());
    }

    public OuvinteDTO update (OuvinteCreateDTO ouvinteAtualizar, Integer id) throws PessoaNaoCadastradaException {
        Ouvinte ouvinteRecuperado = findById(id);
        ouvinteRecuperado.setNome(ouvinteAtualizar.getNome());
        ouvinteRecuperado.setEmail(ouvinteAtualizar.getEmail());
        ouvinteRecuperado.setGenero(ouvinteAtualizar.getGenero());
        ouvinteRecuperado.setDataNascimento(ouvinteAtualizar.getDataNascimento());
        ouvinteRecuperado.setPremium(ouvinteAtualizar.getPremium());
        return converterOuvinte(ouvinteRecuperado);
    }

    public Ouvinte findById (Integer id) throws PessoaNaoCadastradaException {
        Ouvinte ouvinteRecuperado = ouvinteRepository.list().stream()
                .filter(ouvinte -> ouvinte.getIdUser().equals(id))
                .findFirst()
                .orElseThrow(() -> new PessoaNaoCadastradaException("Pessoa não econtrada"));
        return ouvinteRecuperado;
    }

    public void delete (Integer id) throws PessoaNaoCadastradaException {
        Ouvinte ouvinteRecuperado = findById(id);
        ouvinteRepository.list().remove(ouvinteRecuperado);
    }

    public List<OuvinteDTO> listByName (String nome){
        return ouvinteRepository.list().stream()
                .filter(ouvinte -> ouvinte.getNome().toUpperCase().contains(nome.toUpperCase()))
                .map(this::converterOuvinte)
                .collect(Collectors.toList());
    }

}
