package controleGastos.Gestao.fearures.pessoa.controller;


import controleGastos.Gestao.fearures.pessoa.dto.DeleteDTO;
import controleGastos.Gestao.fearures.pessoa.dto.PessoaDTO;
import controleGastos.Gestao.fearures.pessoa.service.PessoaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @GetMapping
    public ResponseEntity<List<PessoaDTO>> listarPessoa(){

        List<PessoaDTO> pessoaDTO = pessoaService.listarPessoas();

        return ResponseEntity.ok(pessoaDTO);

    }

    @PostMapping
    public ResponseEntity<PessoaDTO> criarPessoa(@RequestBody @Valid PessoaDTO pessoaDTO, UriComponentsBuilder uri){

        PessoaDTO pessoaDTO1 = pessoaService.criarPessoa(pessoaDTO);

        var uri1 = uri.path("/pessoa/{id}").buildAndExpand(pessoaDTO1.id()).toUri();

        return ResponseEntity.created(uri1).body(pessoaDTO1);

    }


    @PutMapping("/{id}")
    public ResponseEntity<PessoaDTO> alterarPessoa(@PathVariable @Valid Long id, @RequestBody PessoaDTO pessoaDTO){

        PessoaDTO pessoaDTO1 = pessoaService.alterarPessoa(id, pessoaDTO);

        return ResponseEntity.ok(pessoaDTO1);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteDTO> deletarPessoa(@PathVariable @Positive @Valid Long id){

        DeleteDTO deleteDTO = pessoaService.deletarPessoa(id);

        return ResponseEntity.ok(deleteDTO);

    }


}
