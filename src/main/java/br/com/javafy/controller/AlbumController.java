package br.com.javafy.controller;

import br.com.javafy.dto.albums.AlbumDTO;
import br.com.javafy.service.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/album")
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumService albumService;

    @GetMapping()
    public ResponseEntity<AlbumDTO> getAlbums() {
        return ResponseEntity.ok(albumService.getAlbum());
    }
}
