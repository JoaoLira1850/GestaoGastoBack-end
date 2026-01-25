package controleGastos.Gestao.fearures.planejamento.controller;


import controleGastos.Gestao.fearures.pessoa.dto.DeleteDTO;
import controleGastos.Gestao.fearures.planejamento.dto.PlanejamentoDTO;
import controleGastos.Gestao.fearures.planejamento.service.PlanejamentoService;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plan")
public class PlanejamentoController {


    @Autowired
    private PlanejamentoService planejamentoService;


    @PostMapping
    public ResponseEntity<PlanejamentoDTO> criarPlan(@RequestBody  PlanejamentoDTO planejamentoDTO){

        PlanejamentoDTO plan = planejamentoService.criarPlanejemamento(planejamentoDTO);


        return ResponseEntity.ok(plan);

    }

    @GetMapping
    public ResponseEntity<List<PlanejamentoDTO>> listarPlanejamentos(){

       List<PlanejamentoDTO> planejamentoDTOS = planejamentoService.listarPlanejamentos();

       return ResponseEntity.ok(planejamentoDTOS);

    }

    @PutMapping("{id}")
    public ResponseEntity<PlanejamentoDTO> atualizarPlanjamento(@PathVariable @Positive Long id,
                                                                @RequestBody PlanejamentoDTO planejamentoDTO){

        PlanejamentoDTO planejamentoDTO1 = planejamentoService.atualizarPlanejamento(id, planejamentoDTO);

        return ResponseEntity.ok(planejamentoDTO1);

    }


    @DeleteMapping("{id}")
    public ResponseEntity<DeleteDTO> deletaPlan(@PathVariable @Positive Long id
                                                ){

        DeleteDTO deleteDTO = planejamentoService.deletarPlanejamento(id);

        return ResponseEntity.ok(deleteDTO);


    }
}
