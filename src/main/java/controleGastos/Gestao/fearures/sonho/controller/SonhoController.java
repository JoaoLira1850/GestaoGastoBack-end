package controleGastos.Gestao.fearures.sonho.controller;


import controleGastos.Gestao.fearures.pessoa.dto.DeleteDTO;
import controleGastos.Gestao.fearures.sonho.dto.SonhoDTO;
import controleGastos.Gestao.fearures.sonho.service.SonhoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/sonho")
public class SonhoController {



    @Autowired
    private SonhoService sonhoService;


    @GetMapping
    public ResponseEntity<List<SonhoDTO>> listarSonho(){

        List<SonhoDTO> sonhoDTOList =sonhoService.listarSonhos();

        return ResponseEntity.ok(sonhoDTOList);


    }

    @PostMapping
    public ResponseEntity<SonhoDTO> criarSonho(@RequestBody @Valid SonhoDTO sonhoDTO, UriComponentsBuilder uriBuider){

        SonhoDTO sonhoDTO1 = sonhoService.criarSonho(sonhoDTO);

        var uri = uriBuider.path("/sonho/{id}").buildAndExpand(sonhoDTO1.id()).toUri();

        return ResponseEntity.created(uri).body(sonhoDTO1);

    }

    @PutMapping("/{id}")
    public ResponseEntity<SonhoDTO> atualizarSonho(@PathVariable @Positive Long id, @RequestBody SonhoDTO sonhoDTO){

        SonhoDTO sonhoAterado = sonhoService.alterarSonho(id, sonhoDTO);

        return ResponseEntity.ok(sonhoAterado);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<DeleteDTO> deletarSonho(@PathVariable @Positive Long id){

        DeleteDTO sonhoDeletado = sonhoService.deletarSonho(id);

        return ResponseEntity.ok(sonhoDeletado);
    }
}
