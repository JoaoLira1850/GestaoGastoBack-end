package controleGastos.Gestao.fearures.receita.controller;


import controleGastos.Gestao.fearures.pessoa.dto.DeleteDTO;
import controleGastos.Gestao.fearures.receita.dto.ReceitaDTO;
import controleGastos.Gestao.fearures.receita.service.ReceitaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/receita")
public class ReceitaController {

    @Autowired
    private ReceitaService receitaService;


    @GetMapping
    public ResponseEntity<List<ReceitaDTO>> listarReceitas(){

        List<ReceitaDTO> receitaDTO = receitaService.listarReceita();

        return ResponseEntity.ok(receitaDTO);


    }


    @PostMapping
    public ResponseEntity<ReceitaDTO> criarReceita(@RequestBody @Valid  ReceitaDTO receitaDTO, UriComponentsBuilder uri){

        ReceitaDTO receitaDTO1 = receitaService.criarReceita(receitaDTO);

        var uris = uri.path("/receita/{id}").buildAndExpand(receitaDTO1.id()).toUri();

        return ResponseEntity.created(uris).body(receitaDTO1);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ReceitaDTO> atualizarReceita(@PathVariable @Positive Long id, @RequestBody ReceitaDTO receitaDTO){


        ReceitaDTO receitaDTO1 = receitaService.altualizarReceita(id, receitaDTO);

        return ResponseEntity.ok(receitaDTO1);



    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteDTO> deletarReceita(@PathVariable @Positive Long id){

        DeleteDTO deleteDTO = receitaService.deletarReceita(id);


        return ResponseEntity.ok(deleteDTO);


    }



}
