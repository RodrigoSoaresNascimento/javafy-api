package br.com.javafy.service;

import br.com.javafy.dto.PageDTO;
import br.com.javafy.dto.playlist.PlayListCreate;
import br.com.javafy.dto.playlist.PlayListDTO;
import br.com.javafy.dto.playlist.PlaylistAddMusicaDTO;
import br.com.javafy.entity.MusicaEntity;
import br.com.javafy.entity.PlayListEntity;
import br.com.javafy.entity.UsuarioEntity;
import br.com.javafy.exceptions.PessoaNaoCadastradaException;
import br.com.javafy.exceptions.PlaylistException;
import br.com.javafy.exceptions.SpotifyException;
import br.com.javafy.repository.PlayListRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class PlayListService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PlayListRepository playListRepository;

    @Autowired
    private MusicaService musicaService;

    @Autowired
    private UsuarioService usuarioService;

    public PlayListDTO converterParaPlaylistDTO (PlayListEntity playList){
        return objectMapper.convertValue(playList,PlayListDTO.class);
    }

    public PlayListEntity converterParaPlaylist (PlayListCreate playList){
        return objectMapper.convertValue(playList, PlayListEntity.class);
    }

    public PlayListEntity retornaPlaylistEntityById(Integer id) throws PlaylistException {
        return playListRepository
                .findById(id)
                .orElseThrow(() -> new PlaylistException("Playlist Não Cadastrada"));
    }

    public PlayListDTO getPlaylistWithIdWithMusics (Integer idPlaylist)
            throws PlaylistException {
        return getMusicaForPlaylist(retornaPlaylistEntityById(idPlaylist));
    }

    public PlayListDTO getPlaylistWithIdWithNotMusics (Integer idPlaylist)
            throws PlaylistException {
        return converterParaPlaylistDTO(retornaPlaylistEntityById(idPlaylist));
    }

    public PageDTO<PlayListDTO> getListPlayList (Integer pagina, Integer registro)  {
        PageRequest pageRequest = PageRequest.of(pagina, registro);
        Page<PlayListEntity> page=  playListRepository.findAll(pageRequest);
        List<PlayListDTO> playlistsDTOs = page.getContent()
                .stream()
                .map(p-> {
                    try {
                        return getMusicaForPlaylist(p);
                    } catch (PlaylistException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .toList();
        return new PageDTO<>(page.getTotalElements(),
                page.getTotalPages(), pagina, registro, playlistsDTOs);
    }

    public PlayListDTO create (PlayListCreate playlistCreate, Integer idUsuario)
            throws PessoaNaoCadastradaException, PlaylistException, SpotifyException {

        UsuarioEntity usuario = usuarioService.retornaUsuarioEntityById(idUsuario);

//        if(usuario.getPlano() == TiposdePlano.FREE){
//            throw new PlaylistException("Plano Free, mude seu plano para premium");
//        }

        PlayListEntity playList = converterParaPlaylist(playlistCreate);

        playList.setUsuario(usuario);
        playList = playListRepository.save(playList);

        return converterParaPlaylistDTO(playList);
    }

    public PlayListDTO update(Integer idPlaylist, PlaylistAddMusicaDTO playlistCreate)
            throws PlaylistException, SpotifyException {

        PlayListEntity playList  = retornaPlaylistEntityById(idPlaylist);

        if(playlistCreate.getName() != null){
            playList.setName(playlistCreate.getName());
        }

        if(playlistCreate.getMusicas() != null){
            PlayListEntity finalPlayList = playList;

            Set<MusicaEntity> playlistMusicaEntities = playlistCreate.getMusicas()
                    .stream()
                    .map(m-> new MusicaEntity(m.getIdMusica(), Set.of(finalPlayList)))
                    .collect(Collectors.toSet());

            musicaService.saveMusicaRepository(playlistMusicaEntities);
            Set<MusicaEntity> pla = playList.getMusicas();
            pla.addAll(playlistMusicaEntities);
            playList.setMusicas(pla);
        }

        playList = playListRepository.save(playList);
        return getMusicaForPlaylist(playList);
    }

    public void delete (Integer idPlaylist)
            throws PessoaNaoCadastradaException, PlaylistException {
        playListRepository.delete(retornaPlaylistEntityById(idPlaylist));
    }

    public void removerMusica (Integer idPlaylist, String idMusica)
            throws PlaylistException {

        PlayListEntity playList = retornaPlaylistEntityById(idPlaylist);
        Set<MusicaEntity> musicaEntities = playList.getMusicas();
        musicaEntities.removeIf(m-> idMusica.equals(m.getIdMusica()));
        playList.setMusicas(musicaEntities);
        playListRepository.save(playList);
    }

    private PlayListDTO getMusicaForPlaylist(PlayListEntity playList)
            throws PlaylistException {

        PlayListDTO playListDTO = converterParaPlaylistDTO(playList);
        String ids = playList.getMusicas()
                .stream().map(MusicaEntity::getIdMusica)
                .collect(Collectors.joining(","));

        if(!ids.isEmpty()){
            playListDTO.setMusicas(musicaService.getList(ids));
        }
        return playListDTO;

    }

}
