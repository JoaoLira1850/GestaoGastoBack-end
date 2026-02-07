package controleGastos.Gestao.fearures.despesas.controller;


import controleGastos.Gestao.fearures.despesas.dto.DespesaDTO;
import controleGastos.Gestao.fearures.despesas.service.DespesaService;
import controleGastos.Gestao.fearures.pessoa.dto.DeleteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/despesa")
public class DespesaController {

    @Autowired
    private DespesaService despesaService;


    @GetMapping
    public ResponseEntity< List<DespesaDTO>> listDespesa(){

        List<DespesaDTO> despesaDTOList = despesaService.listDespesas();

        return ResponseEntity.ok(despesaDTOList);

    }

    @PostMapping
    public ResponseEntity<DespesaDTO> createDespesa(@RequestBody DespesaDTO despesaDTO, UriComponentsBuilder uri){

        DespesaDTO despesaDTO1 = despesaService.createDespesa(despesaDTO);

        var uris = uri.path("/despesa/{id}").buildAndExpand(despesaDTO1.id()).toUri();

        return ResponseEntity.created(uris).body(despesaDTO1);


    }

    @PutMapping("/{id}")
    public ResponseEntity<DespesaDTO> updatedDespesa(@PathVariable Long id, @RequestBody DespesaDTO despesaDTO){

        DespesaDTO despesaDTO1 = despesaService.updateDespesa(id, despesaDTO);

        return ResponseEntity.ok(despesaDTO1);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteDTO> deletedDespesa(Long id){
        DeleteDTO deleteDTO = despesaService.deleteDespesa(id);
        return ResponseEntity.ok(deleteDTO);

    }


}
