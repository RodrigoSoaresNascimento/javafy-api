package br.com.javafy.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/seguidores")
public class SeguidoresController {

    @GetMapping(value = "/from-user/{idUser}")// meus seuindo quem eu sigo
    public void fromUser(@PathVariable("idUser") Integer idUser){

    }

    @GetMapping(value = "/to-user/{idUser}") // quem me segue
    public void toUser(@PathVariable("idUser") Integer idUser){

    }
}
